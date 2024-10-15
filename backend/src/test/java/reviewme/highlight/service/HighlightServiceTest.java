package reviewme.highlight.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.QuestionFixture;
import reviewme.highlight.entity.Highlight;
import reviewme.highlight.entity.HighlightRange;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightIndexRangeRequest;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class HighlightServiceTest {

    @Autowired
    private HighlightService highlightService;

    @Autowired
    private HighlightRepository highlightRepository;

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
    void 하이라이트_반영을_요청하면_리뷰_그룹과_질문에_해당하는_기존_하이라이트를_모두_삭제한다() {
        // given
        long questionId = questionRepository.save(서술형_필수_질문()).getId();
        long sectionId = sectionRepository.save(항상_보이는_섹션(List.of(questionId))).getId();
        long templateId = templateRepository.save(템플릿(List.of(sectionId))).getId();
        String reviewRequestCode = "reviewRequestCode";
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, "groupAccessCode"));

        TextAnswer textAnswer1 = new TextAnswer(questionId, "text answer1");
        TextAnswer textAnswer2 = new TextAnswer(questionId, "text answer2");
        Review review = reviewRepository.save(new Review(templateId, reviewGroup.getId(), List.of(textAnswer1, textAnswer2)));
        Highlight highlight = highlightRepository.save(new Highlight(textAnswer1.getId(), 1, new HighlightRange(1, 1)));

        HighlightIndexRangeRequest indexRangeRequest = new HighlightIndexRangeRequest(1, 1);
        HighlightedLineRequest lineRequest = new HighlightedLineRequest(0, List.of(indexRangeRequest));
        HighlightRequest highlightRequest1 = new HighlightRequest(textAnswer2.getId(), List.of(lineRequest));
        HighlightsRequest highlightsRequest = new HighlightsRequest(questionId, List.of(highlightRequest1));

        // when
        highlightService.editHighlight(highlightsRequest, reviewGroup);

        // then
        assertAll(() -> assertThat(highlightRepository.existsById(highlight.getId())).isFalse());
    }

    @Test
    void 하이라이트_반영을_요청하면_새로운_하이라이트가_저장된다() {
        // given
        long questionId = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        long sectionId = sectionRepository.save(항상_보이는_섹션(List.of(questionId))).getId();
        long templateId = templateRepository.save(템플릿(List.of(sectionId))).getId();
        String reviewRequestCode = "reviewRequestCode";
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, "groupAccessCode"));


        TextAnswer textAnswer = new TextAnswer(questionId, "text answer1");
        Review review = reviewRepository.save(new Review(templateId, reviewGroup.getId(), List.of(textAnswer)));
        highlightRepository.save(new Highlight(1, 1, new HighlightRange(1, 1)));

        int startIndex = 2;
        int endIndex = 2;
        HighlightIndexRangeRequest indexRangeRequest = new HighlightIndexRangeRequest(startIndex, endIndex);
        HighlightedLineRequest lineRequest = new HighlightedLineRequest(0, List.of(indexRangeRequest));
        HighlightRequest highlightRequest = new HighlightRequest(textAnswer.getId(), List.of(lineRequest));
        HighlightsRequest highlightsRequest = new HighlightsRequest(questionId, List.of(highlightRequest));

        // when
        highlightService.editHighlight(highlightsRequest, reviewGroup);

        // then
        List<Highlight> highlights = highlightRepository.findAll();
        assertAll(
                () -> assertThat(highlights.get(0).getAnswerId()).isEqualTo(textAnswer.getId()),
                () -> assertThat(highlights.get(0).getHighlightRange()).isEqualTo(
                        new HighlightRange(startIndex, endIndex))
        );
    }
}
