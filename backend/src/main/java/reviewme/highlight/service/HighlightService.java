package reviewme.highlight.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightIndexRangeRequest;
import reviewme.highlight.service.dto.HighlightRequest;
import reviewme.highlight.service.dto.HighlightedLineRequest;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.validator.HighlightValidator;
import reviewme.review.domain.Answer;
import reviewme.review.repository.AnswerRepository;
import reviewme.reviewgroup.domain.ReviewGroup;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;
    private final AnswerRepository answerRepository;

    private final HighlightValidator highlightValidator;

    @Transactional
    public void highlight(HighlightsRequest request, ReviewGroup reviewGroup) {
        long reviewGroupId = reviewGroup.getId();
        highlightValidator.validate(request, reviewGroupId);
        deleteOldHighlight(request.questionId(), reviewGroupId);
        saveNewHighlight(request);
    }

    private void deleteOldHighlight(long questionId, long reviewGroupId) {
        Set<Answer> answersByReviewGroup = answerRepository.findAllByReviewGroupId(reviewGroupId);
        List<Long> answersByReviewQuestion = answersByReviewGroup.stream()
                .filter(answer -> answer.getQuestionId() == questionId)
                .map(Answer::getId)
                .toList();

        highlightRepository.deleteAllById(answersByReviewQuestion);
    }

    private void saveNewHighlight(HighlightsRequest highlightsRequest) {
        List<Highlight> highlights = new ArrayList<>();
        for (HighlightRequest highlight : highlightsRequest.highlights()) {
            for (HighlightedLineRequest line : highlight.lines()) {
                for (HighlightIndexRangeRequest range : line.ranges()) {
                    Highlight highLight = new Highlight(highlight.answerId(),
                            line.index(), range.startIndex(), range.endIndex());
                    highlights.add(highLight);
                }
            }
        }
        highlightRepository.saveAll(highlights);
    }
}
