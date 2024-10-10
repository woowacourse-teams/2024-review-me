package reviewme.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Answer;
import reviewme.review.repository.AnswerRepository;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredBySectionResponse;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.exception.SectionNotFoundInTemplateException;
import reviewme.review.service.mapper.ReviewGatherMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Section;
import reviewme.template.repository.SectionRepository;

@Service
@RequiredArgsConstructor
public class ReviewGatheredLookupService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final SectionRepository sectionRepository;

    private final ReviewGatherMapper reviewGatherMapper;

    @Transactional(readOnly = true)
    public ReviewsGatheredBySectionResponse getReceivedReviewsBySectionId(String reviewRequestCode, long sectionId) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
        Section section = sectionRepository.findByIdAndTemplateId(sectionId, reviewGroup.getTemplateId())
                .orElseThrow(() -> new SectionNotFoundInTemplateException(sectionId, reviewGroup.getTemplateId()));

        Map<Long, Question> questionIdQuestion = questionRepository
                .findAllBySectionIdOrderByPosition(section.getId()).stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));
        List<Answer> receivedAnswers = answerRepository.findReceivedAnswersByQuestionIds(
                reviewGroup.getId(), new ArrayList<>(questionIdQuestion.keySet()));
        Map<Long, List<Answer>> questionIdAnswers = receivedAnswers.stream()
                .collect(Collectors.groupingBy(Answer::getQuestionId));

        Map<Question, List<Answer>> questionAnswers = questionIdQuestion.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, entry -> questionIdAnswers.getOrDefault(entry.getKey(), List.of())));

        return new ReviewsGatheredBySectionResponse(reviewGatherMapper.mapToResponseBySection(questionAnswers));
    }
}
