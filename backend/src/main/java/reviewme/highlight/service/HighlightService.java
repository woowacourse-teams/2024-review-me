package reviewme.highlight.service;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.highlight.service.dto.HighlightsRequest;
import reviewme.highlight.service.mapper.HighlightMapper;
import reviewme.review.repository.AnswerRepository;
import reviewme.reviewgroup.domain.ReviewGroup;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;
    private final AnswerRepository answerRepository;

    private final HighlightMapper highlightMapper;

    @Transactional
    public void editHighlight(HighlightsRequest highlightsRequest, ReviewGroup reviewGroup) {
        // TODO: request으로부터 아래 두 가지를 검증한다.
        // 1. answerId들이 각각 question에 포함되는지 확인한다.
        // 2. answerId들이 각각 reviewGroup에 포함되는지 확인한다.
        // validator 내부의 메서드도 재사용 가능하도록 작성되어야 한다.
        // 하지만 현재 각 validator들은 하나의 기능을 위해 존재하며, 리팩터링에 어려움이 있다.

        List<Long> requestedAnswerIds = highlightsRequest.getUniqueAnswerIds();

        List<Highlight> highlights = highlightMapper.mapToHighlights(highlightsRequest);

        Set<Long> answerIds = answerRepository.findIdsByQuestionId(highlightsRequest.questionId());
        highlightRepository.deleteAllByAnswerIds(answerIds);
        highlightRepository.saveAll(highlights);
    }
}
