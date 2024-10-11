package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.선택형_옵션_질문;

import java.util.List;
import java.util.Map;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredByQuestionResponse;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredBySectionResponse;
import reviewme.review.service.dto.response.gathered.SimpleQuestionResponse;
import reviewme.review.service.dto.response.gathered.TextResponse;
import reviewme.review.service.dto.response.gathered.VoteResponse;
import reviewme.support.ServiceTest;
import reviewme.template.repository.SectionRepository;

@ServiceTest
class ReviewGatherMapperTest {

    @Autowired
    private ReviewGatherMapper reviewGatherMapper;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 질문과_하위_답변을_규칙에_맞게_반환한다() {
        // given
        Question question1 = questionRepository.save(서술형_옵션_질문(1));
        Question question2 = questionRepository.save(선택형_옵션_질문(2));
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question2.getId()));
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId()));
        optionItemRepository.saveAll(List.of(optionItem1, optionItem2));

        TextAnswer textAnswer1 = new TextAnswer(question1.getId(), "프엔 서술형 답변");
        TextAnswer textAnswer2 = new TextAnswer(question1.getId(), "백엔드 서술형 답변");
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(
                question2.getId(), List.of(optionItem1.getId(), optionItem2.getId()));
        reviewRepository.save(new Review(1L, 1L, List.of(textAnswer1, textAnswer2, checkboxAnswer)));

        // when
        ReviewsGatheredBySectionResponse actual = reviewGatherMapper.mapToReviewsGatheredBySection(Map.of(
                question1, List.of(textAnswer1, textAnswer2),
                question2, List.of(checkboxAnswer)));

        // then
        assertAll(
                () -> 질문의_수만큼_반환한다(actual, 2),
                () -> 질문의_내용을_반환한다(actual, question1.getContent(), question2.getContent()),
                () -> 서술형_답변을_반환한다(actual, "프엔 서술형 답변", "백엔드 서술형 답변"),
                () -> 선택형_답변을_반환한다(actual,
                        Tuple.tuple(optionItem1.getContent(), 1L),
                        Tuple.tuple(optionItem2.getContent(), 1L))
        );
    }

    @Test
    void 서술형_질문에_답변이_없으면_질문_정보는_반환하되_답변은_빈_배열로_반환한다() {
        // given
        Question question1 = questionRepository.save(서술형_옵션_질문(1));
        Question question2 = questionRepository.save(서술형_옵션_질문(2));

        // when
        ReviewsGatheredBySectionResponse actual = reviewGatherMapper.mapToReviewsGatheredBySection(Map.of(
                question1, List.of(),
                question2, List.of()));

        // then
        assertAll(
                () -> 질문의_수만큼_반환한다(actual, 2),
                () -> 질문의_내용을_반환한다(actual, question1.getContent(), question2.getContent()),
                () -> assertThat(actual.reviews())
                        .flatExtracting(ReviewsGatheredByQuestionResponse::answers)
                        .isEmpty()
        );
    }

    private void 질문의_수만큼_반환한다(ReviewsGatheredBySectionResponse actual, int expectedSize) {
        assertThat(actual.reviews()).hasSize(expectedSize);
    }

    private void 질문의_내용을_반환한다(ReviewsGatheredBySectionResponse actual, String... expectedContents) {
        assertThat(actual.reviews())
                .extracting(ReviewsGatheredByQuestionResponse::question)
                .extracting(SimpleQuestionResponse::name)
                .containsExactly(expectedContents);
    }

    private void 서술형_답변을_반환한다(ReviewsGatheredBySectionResponse actual, String... expectedAnswerContents) {
        List<TextResponse> textResponse = actual.reviews()
                .stream()
                .filter(review -> review.answers() != null)
                .flatMap(reviewsGatheredByQuestionResponse -> reviewsGatheredByQuestionResponse.answers().stream())
                .toList();
        assertThat(textResponse).extracting(TextResponse::content).containsExactly(expectedAnswerContents);
    }

    private void 선택형_답변을_반환한다(ReviewsGatheredBySectionResponse actual, Tuple... expectedVotes) {
        List<VoteResponse> voteResponses = actual.reviews()
                .stream()
                .filter(review -> review.votes() != null)
                .flatMap(reviewsGatheredByQuestionResponse -> reviewsGatheredByQuestionResponse.votes().stream())
                .toList();
        assertThat(voteResponses)
                .extracting(VoteResponse::content, VoteResponse::count)
                .containsExactly(expectedVotes);
    }
}
