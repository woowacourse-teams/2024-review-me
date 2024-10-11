package reviewme.highlight.service.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.QuestionFixture;
import reviewme.fixture.ReviewGroupFixture;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.exception.InvalidHighlightLineIndexException;
import reviewme.highlight.service.exception.SubmittedAnswerAndProvidedAnswerMismatchException;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class HighlightValidatorTest {

    @Autowired
    private HighlightValidator highlightValidator;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 하이라이트의_질문_id가_리뷰_그룹의_템플릿에_속한_질문이_아니면_예외를_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹());
        Question question = questionRepository.save(QuestionFixture.서술형_필수_질문());
        HighlightsRequest highlightsRequest = new HighlightsRequest(question.getId(), List.of());

        // when && then
        assertThatCode(() -> highlightValidator.validate(highlightsRequest, reviewGroup.getId()))
                .isInstanceOf(SubmittedQuestionAndProvidedQuestionMismatchException.class);
    }

    @Test
    void 하이라이트의_답변_id가_리뷰_그룹에_달린_답변이_아니면_예외를_발생한다() {
        // given
        long questionId = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(questionId)));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        ReviewGroup reviewGroup1 = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹());
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹());
        TextAnswer textAnswer1 = new TextAnswer(questionId, "text answer1");
        TextAnswer textAnswer2 = new TextAnswer(questionId, "text answer2");
        reviewRepository.saveAll(List.of(
                new Review(template.getId(), reviewGroup1.getId(), List.of(textAnswer1)),
                new Review(template.getId(), reviewGroup2.getId(), List.of(textAnswer2))
        ));

        HighlightRequest highlightRequest = new HighlightRequest(textAnswer2.getId(), List.of());
        HighlightsRequest highlightsRequest = new HighlightsRequest(1L, List.of(highlightRequest));

        // when &&  then
        assertThatCode(() -> highlightValidator.validate(highlightsRequest, reviewGroup1.getId()))
                .isInstanceOf(SubmittedAnswerAndProvidedAnswerMismatchException.class);
    }

    @Test
    void 하이라이트의_답변_id가_하이라이트의_질문_id에_해당하는_답변이_아니면_예외를_발생한다() {
        // given
        long questionId1 = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        long questionId2 = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(questionId1, questionId2)));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        long reviewGroupId = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹()).getId();
        TextAnswer textAnswer_Q1 = new TextAnswer(questionId1, "text answer 1");

        HighlightRequest highlightRequest = new HighlightRequest(textAnswer_Q1.getId(), List.of());
        HighlightsRequest highlightsRequest = new HighlightsRequest(questionId2, List.of(highlightRequest));

        // when && then
        assertThatCode(() -> highlightValidator.validate(highlightsRequest, reviewGroupId))
                .isInstanceOf(SubmittedAnswerAndProvidedAnswerMismatchException.class);
    }

    @Test
    void 답변의_줄_수보다_하이라이트의_줄_번호가_더_크면_예외를_발생한다() {
        // given
        long questionId = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        long sectionId = sectionRepository.save(항상_보이는_섹션(List.of(questionId))).getId();
        long templateId = templateRepository.save(템플릿(List.of(sectionId))).getId();

        TextAnswer textAnswer = new TextAnswer(questionId, "line 1\n line 2");
        long reviewGroupId = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹()).getId();
        Review review = reviewRepository.save(new Review(templateId, reviewGroupId, List.of(textAnswer)));

        long answerLineCount = textAnswer.getContent().lines().count();
        HighlightedLineRequest highlightedLineRequest = new HighlightedLineRequest((int) answerLineCount, List.of());
        HighlightRequest highlightRequest = new HighlightRequest(textAnswer.getId(), List.of(highlightedLineRequest));
        HighlightsRequest highlightsRequest = new HighlightsRequest(questionId, List.of(highlightRequest));

        // when & then
        assertThatCode(() -> highlightValidator.validate(highlightsRequest, reviewGroupId))
                .isInstanceOf(InvalidHighlightLineIndexException.class);
    }
}
