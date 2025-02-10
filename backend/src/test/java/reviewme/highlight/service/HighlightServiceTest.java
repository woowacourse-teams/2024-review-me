package reviewme.highlight.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.domain.HighlightRange;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightIndexRangeRequest;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
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
    private TemplateRepository templateRepository;

    @Test
    void 하이라이트_반영을_요청하면_리뷰_그룹과_질문에_해당하는_기존_하이라이트를_모두_삭제한다() {
        // given
        Question question = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        Template template = templateRepository.save(new Template(List.of(section)));
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        TextAnswer textAnswer1 = new TextAnswer(question.getId(), "text answer1");
        TextAnswer textAnswer2 = new TextAnswer(question.getId(), "text answer2");
        reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(textAnswer1, textAnswer2)));
        Highlight highlight = highlightRepository.save(new Highlight(textAnswer1.getId(), 1, new HighlightRange(1, 1)));

        HighlightIndexRangeRequest indexRangeRequest = new HighlightIndexRangeRequest(1, 1);
        HighlightedLineRequest lineRequest = new HighlightedLineRequest(0, List.of(indexRangeRequest));
        HighlightRequest highlightRequest1 = new HighlightRequest(textAnswer2.getId(), List.of(lineRequest));
        HighlightsRequest highlightsRequest = new HighlightsRequest(question.getId(), List.of(highlightRequest1));

        // when
        highlightService.editHighlight(highlightsRequest, reviewGroup);

        // then
        assertAll(() -> assertThat(highlightRepository.existsById(highlight.getId())).isFalse());
    }

    @Test
    void 하이라이트_반영을_요청하면_새로운_하이라이트가_저장된다() {
        // given
        Question question = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        Template template = templateRepository.save(new Template(List.of(section)));
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        TextAnswer textAnswer = new TextAnswer(question.getId(), "text answer1");
        reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(textAnswer)));
        highlightRepository.save(new Highlight(1, 1, new HighlightRange(1, 1)));

        int startIndex = 2;
        int endIndex = 2;
        HighlightIndexRangeRequest indexRangeRequest = new HighlightIndexRangeRequest(startIndex, endIndex);
        HighlightedLineRequest lineRequest = new HighlightedLineRequest(0, List.of(indexRangeRequest));
        HighlightRequest highlightRequest = new HighlightRequest(textAnswer.getId(), List.of(lineRequest));
        HighlightsRequest highlightsRequest = new HighlightsRequest(question.getId(), List.of(highlightRequest));

        // when
        highlightService.editHighlight(highlightsRequest, reviewGroup);

        // then
        List<Highlight> highlights = highlightRepository.findAllByAnswerIdsOrderedAsc(List.of(textAnswer.getId()));
        Highlight actual = highlights.get(0);
        assertAll(
                () -> assertThat(actual.getAnswerId()).isEqualTo(textAnswer.getId()),
                () -> assertThat(actual.getHighlightRange()).isEqualTo(new HighlightRange(startIndex, endIndex))
        );
    }

    @Test
    void 하이라이트_할_내용이_없는_요청이_오면_기존에_있던_내용을_삭제하고_아무것도_저장하지_않는다() {
        // given
        Question question = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        Template template = templateRepository.save(new Template(List.of(section)));
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        TextAnswer textAnswer = new TextAnswer(question.getId(), "text answer1");
        reviewRepository.save(비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(textAnswer)));
        Highlight highlight = highlightRepository.save(new Highlight(textAnswer.getId(), 1, new HighlightRange(1, 1)));

        HighlightsRequest highlightsRequest = new HighlightsRequest(question.getId(), List.of());

        // when
        highlightService.editHighlight(highlightsRequest, reviewGroup);

        // then
        assertAll(() -> assertThat(highlightRepository.existsById(highlight.getId())).isFalse());
    }
}
