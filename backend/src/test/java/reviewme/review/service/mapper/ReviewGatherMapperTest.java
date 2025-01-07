package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.선택형_질문;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import java.util.Map;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewGatherMapperTest {

    @Autowired
    private ReviewGatherMapper reviewGatherMapper;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 질문과_하위_답변을_규칙에_맞게_반환한다() {
        // given
        Question question1 = 서술형_옵션_질문(1);
        Question question2 = 선택형_질문(false, 2, 2);
        Section section = 항상_보이는_섹션(List.of(question1, question2));
        Template template = templateRepository.save(new Template(List.of(section)));
        List<OptionItem> optionItems = question2.getOptionGroup().getOptionItems();

        TextAnswer textAnswer1 = new TextAnswer(question1.getId(), "프엔 서술형 답변");
        CheckboxAnswer checkboxAnswer1 = new CheckboxAnswer(
                question2.getId(),
                optionItems.stream().map(OptionItem::getId).toList() // check all options
        );
        reviewRepository.save(new Review(template.getId(), 1L, List.of(textAnswer1, checkboxAnswer1)));

        // when
        ReviewsGatheredBySectionResponse actual = reviewGatherMapper.mapToReviewsGatheredBySection(Map.of(
                        question1, List.of(textAnswer1),
                        question2, List.of(checkboxAnswer1)),
                List.of()
        );

        // then
        assertAll(
                () -> 질문의_수만큼_반환한다(actual, 2),
                () -> 질문의_내용을_반환한다(actual, question1.getContent(), question2.getContent()),
                () -> 서술형_답변을_반환한다(actual, "프엔 서술형 답변"),
                () -> 선택형_답변을_반환한다(actual,
                        Tuple.tuple(optionItems.get(0).getContent(), 1L),
                        Tuple.tuple(optionItems.get(1).getContent(), 1L))
        );
    }

    @Test
    void 서술형_질문에_답변이_없으면_질문_정보는_반환하되_답변은_빈_배열로_반환한다() {
        // given
        Question question = 서술형_옵션_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        templateRepository.save(new Template(List.of(section)));

        // when
        ReviewsGatheredBySectionResponse actual = reviewGatherMapper.mapToReviewsGatheredBySection(
                Map.of(question, List.of()),
                List.of()
        );

        // then
        assertAll(
                () -> 질문의_수만큼_반환한다(actual, 1),
                () -> 질문의_내용을_반환한다(actual, question.getContent()),
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
