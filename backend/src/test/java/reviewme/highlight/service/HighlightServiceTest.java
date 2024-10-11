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
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.domain.HighlightPosition;
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
        long questionId = questionRepository.save(QuestionFixture.서술형_필수_질문()).getId();
        long sectionId = sectionRepository.save(항상_보이는_섹션(List.of(questionId))).getId();
        long templateId = templateRepository.save(템플릿(List.of(sectionId))).getId();
        String reviewRequestCode = "reviewRequestCode";
        long reviewGroupId = reviewGroupRepository.save(ReviewGroupFixture.리뷰_그룹(reviewRequestCode, "groupAccessCode"))
                .getId();
        Highlight highlight1 = highlightRepository.save(new Highlight(1, 1, 1, 1));
        Highlight highlight2 = highlightRepository.save(new Highlight(2, 1, 1, 1));

        TextAnswer textAnswer1 = new TextAnswer(questionId, "text answer1");
        TextAnswer textAnswer2 = new TextAnswer(questionId, "text answer2");
        Review review = reviewRepository.save(new Review(templateId, reviewGroupId, List.of(textAnswer1, textAnswer2)));

        HighlightIndexRangeRequest indexRangeRequest = new HighlightIndexRangeRequest(1L, 1L);
        HighlightedLineRequest lineRequest = new HighlightedLineRequest(0L, List.of(indexRangeRequest));
        HighlightRequest highlightRequest1 = new HighlightRequest(textAnswer1.getId(), List.of(lineRequest));
        HighlightRequest highlightRequest2 = new HighlightRequest(textAnswer2.getId(), List.of(lineRequest));
        HighlightsRequest highlightsRequest = new HighlightsRequest(
                questionId, List.of(highlightRequest1, highlightRequest2)
        );

        // when
        highlightService.highlight(highlightsRequest, reviewRequestCode);

        // then
        assertAll(
                () -> assertThat(highlightRepository.existsById(highlight1.getId())).isFalse(),
                () -> assertThat(highlightRepository.existsById(highlight2.getId())).isFalse()
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
        highlightRepository.save(new Highlight(1, 1, 1, 1));

        TextAnswer textAnswer1 = new TextAnswer(questionId, "text answer1");
        TextAnswer textAnswer2 = new TextAnswer(questionId, "text answer2");
        Review review = reviewRepository.save(new Review(templateId, reviewGroupId, List.of(textAnswer1, textAnswer2)));

        long startIndex = 2L;
        long endIndex = 2L;
        long lineIndex = 0;
        HighlightIndexRangeRequest indexRangeRequest = new HighlightIndexRangeRequest(startIndex, endIndex);
        HighlightedLineRequest lineRequest1 = new HighlightedLineRequest(lineIndex, List.of(indexRangeRequest));
        HighlightedLineRequest lineRequest2 = new HighlightedLineRequest(lineIndex, List.of(indexRangeRequest));
        HighlightRequest highlightRequest1 = new HighlightRequest(textAnswer1.getId(), List.of(lineRequest1));
        HighlightRequest highlightRequest2 = new HighlightRequest(textAnswer2.getId(), List.of(lineRequest2));
        HighlightsRequest highlightsRequest = new HighlightsRequest(questionId,
                List.of(highlightRequest1, highlightRequest2));

        // when
        highlightService.highlight(highlightsRequest, reviewRequestCode);

        // then
        List<Highlight> highlights = highlightRepository.findAll();
        HighlightPosition position = new HighlightPosition((int) lineIndex, (int) startIndex, (int) endIndex);
        assertAll(
                () -> assertThat(highlights.get(0).getAnswerId()).isEqualTo(textAnswer1.getId()),
                () -> assertThat(highlights.get(1).getAnswerId()).isEqualTo(textAnswer2.getId()),
                () -> assertThat(highlights.get(0).getHighlightPosition()).isEqualTo(position),
                () -> assertThat(highlights.get(0).getHighlightPosition()).isEqualTo(position)
        );
    }
}
