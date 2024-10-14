package reviewme.review.service;

import java.util.LinkedHashMap;
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
import reviewme.review.service.exception.SectionNotFoundInTemplateException;
import reviewme.review.service.mapper.ReviewGatherMapper;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Section;
import reviewme.template.repository.SectionRepository;

@Service
@RequiredArgsConstructor
public class ReviewGatheredLookupService {

    private static final int ANSWER_RESPONSE_LIMIT = 100;

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final SectionRepository sectionRepository;

    private final ReviewGatherMapper reviewGatherMapper;

    @Transactional(readOnly = true)
    public ReviewsGatheredBySectionResponse getReceivedReviewsBySectionId(ReviewGroup reviewGroup, long sectionId) {
        Section section = getSectionOrThrow(sectionId, reviewGroup);
        Map<Question, List<Answer>> questionAnswers = getQuestionAnswers(section, reviewGroup);

        return reviewGatherMapper.mapToReviewsGatheredBySection(questionAnswers);
    }

    private Section getSectionOrThrow(long sectionId, ReviewGroup reviewGroup) {
        return sectionRepository.findByIdAndTemplateId(sectionId, reviewGroup.getTemplateId())
                .orElseThrow(() -> new SectionNotFoundInTemplateException(sectionId, reviewGroup.getTemplateId()));
    }

    private Map<Question, List<Answer>> getQuestionAnswers(Section section, ReviewGroup reviewGroup) {
        List<Question> questions = questionRepository.findAllBySectionIdOrderByPosition(section.getId());
        Map<Long, Question> questionIdQuestion = new LinkedHashMap<>();
        questions.forEach(question -> questionIdQuestion.put(question.getId(), question));

        Map<Long, List<Answer>> questionIdAnswers = answerRepository
                .findReceivedAnswersByQuestionIds(reviewGroup.getId(), questionIdQuestion.keySet(),
                        ANSWER_RESPONSE_LIMIT)
                .stream()
                .collect(Collectors.groupingBy(Answer::getQuestionId));

        return questionIdQuestion.values().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        question -> questionIdAnswers.getOrDefault(question.getId(), List.of())
                ));
    }
}
