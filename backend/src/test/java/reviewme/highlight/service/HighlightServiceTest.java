package reviewme.highlight.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.QuestionFixture;
import reviewme.fixture.ReviewGroupFixture;
import reviewme.highlight.domain.HighLight;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightIndexRangeRequest;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class HighlightServiceTest {

    @Autowired
    HighlightService highlightService;

    @Autowired
    HighlightRepository highlightRepository;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Test
    void 하이라이트_반영을_요청하면_리뷰_그룹과_질문에_해당하는_기존_하이라이트를_모두_삭제한다() {
        // given
        long questionId = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        long sectionId = sectionRepository.save(항상_보이는_섹션(List.of(questionId))).getId();
        long templateId = templateRepository.save(템플릿(List.of(sectionId))).getId();
        String reviewRequestCode = "reviewRequestCode";
        long reviewGroupId = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹(reviewRequestCode, "groupAccessCode"))
                .getId();
        HighLight highLight1 = highlightRepository.save(new HighLight(reviewGroupId, questionId, 1, 1, 1, 1));
        HighLight highLight2 = highlightRepository.save(new HighLight(reviewGroupId, questionId, 2, 1, 1, 1));

        TextAnswer textAnswer1 = new TextAnswer(questionId, "text answer1");
        TextAnswer textAnswer2 = new TextAnswer(questionId, "text answer2");
        Review review = reviewRepository.save(new Review(templateId, reviewGroupId, List.of(textAnswer1, textAnswer2)));

        HighlightIndexRangeRequest indexRangeRequest = new HighlightIndexRangeRequest(1L, 1L);
        HighlightedLineRequest lineRequest = new HighlightedLineRequest(1L, List.of(indexRangeRequest));
        HighlightRequest highlightRequest1 = new HighlightRequest(textAnswer1.getId(), List.of(lineRequest));
        HighlightRequest highlightRequest2 = new HighlightRequest(textAnswer2.getId(), List.of(lineRequest));
        HighlightsRequest highlightsRequest = new HighlightsRequest(
                questionId, List.of(highlightRequest1, highlightRequest2)
        );

        // when
        highlightService.highlight(highlightsRequest, reviewRequestCode);

        // then
        assertAll(
                () -> assertThat(highlightRepository.existsById(highLight1.getId())).isFalse(),
                () -> assertThat(highlightRepository.existsById(highLight2.getId())).isFalse()
        );
    }

    @Test
    void 하이라이트_반영을_요청하면_새로운_하이라이트가_저장된다() {
        // given
        long questionId = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        long sectionId = sectionRepository.save(항상_보이는_섹션(List.of(questionId))).getId();
        long templateId = templateRepository.save(템플릿(List.of(sectionId))).getId();
        String reviewRequestCode = "reviewRequestCode";
        long reviewGroupId = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹(reviewRequestCode, "groupAccessCode"))
                .getId();
        highlightRepository.save(new HighLight(reviewGroupId, questionId, 1, 1, 1, 1));

        TextAnswer textAnswer1 = new TextAnswer(questionId, "text answer1");
        TextAnswer textAnswer2 = new TextAnswer(questionId, "text answer2");
        Review review = reviewRepository.save(new Review(templateId, reviewGroupId, List.of(textAnswer1, textAnswer2)));

        long startIndex = 2L;
        long endIndex = 2L;
        long lineIndex = 1L;
        HighlightIndexRangeRequest indexRangeRequest = new HighlightIndexRangeRequest(startIndex, endIndex);
        HighlightedLineRequest lineRequest = new HighlightedLineRequest(lineIndex, List.of(indexRangeRequest));
        HighlightRequest highlightRequest1 = new HighlightRequest(textAnswer1.getId(), List.of(lineRequest));
        HighlightRequest highlightRequest2 = new HighlightRequest(textAnswer2.getId(), List.of(lineRequest));
        HighlightsRequest highlightsRequest = new HighlightsRequest(
                questionId, List.of(highlightRequest1, highlightRequest2)
        );

        // when
        highlightService.highlight(highlightsRequest, reviewRequestCode);

        // then
        List<HighLight> highLights = highlightRepository.findAll();
        assertAll(
                () -> assertThat(highLights.get(0).getQuestionId()).isEqualTo(questionId),
                () -> assertThat(highLights.get(1).getQuestionId()).isEqualTo(questionId),
                () -> assertThat(highLights.get(0).getReviewGroupId()).isEqualTo(reviewGroupId),
                () -> assertThat(highLights.get(1).getReviewGroupId()).isEqualTo(reviewGroupId),
                () -> assertThat(highLights.get(0).getAnswerId()).isEqualTo(textAnswer1.getId()),
                () -> assertThat(highLights.get(1).getAnswerId()).isEqualTo(textAnswer2.getId()),
                () -> assertThat(highLights.get(0).getLineIndex()).isEqualTo(lineIndex),
                () -> assertThat(highLights.get(1).getLineIndex()).isEqualTo(lineIndex),
                () -> assertThat(highLights.get(0).getStartIndex()).isEqualTo(startIndex),
                () -> assertThat(highLights.get(1).getStartIndex()).isEqualTo(startIndex),
                () -> assertThat(highLights.get(0).getEndIndex()).isEqualTo(endIndex),
                () -> assertThat(highLights.get(1).getEndIndex()).isEqualTo(endIndex)
        );
    }
}
