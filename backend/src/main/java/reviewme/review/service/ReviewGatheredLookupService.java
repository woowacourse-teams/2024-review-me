package reviewme.review.service;

import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.AnswerRepository;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredByQuestionResponse;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredBySectionResponse;
import reviewme.review.service.dto.response.gathered.SimpleQuestionResponse;
import reviewme.review.service.dto.response.gathered.TextResponse;
import reviewme.review.service.dto.response.gathered.VoteResponse;

@Service
@RequiredArgsConstructor
public class ReviewGatheredLookupService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public ReviewsGatheredBySectionResponse getReceivedReviewsBySectionId(String reviewRequestCode, long sectionId) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
        validateSectionId(sectionId, reviewGroup);

        Map<Long, Question> questionIdQuestion = questionRepository
                .findAllBySectionId(sectionId).stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));
        List<Answer> receivedAnswers = answerRepository.findReceivedAnswersByQuestionIds(
                reviewGroup.getId(), new ArrayList<>(questionIdQuestion.keySet()));
        Map<Long, List<Answer>> questionIdAnswers = receivedAnswers.stream()
                .collect(Collectors.groupingBy(Answer::getQuestionId));

        ArrayList<Long> questionIds = new ArrayList<>(questionIdQuestion.keySet());
        Map<Question, List<Answer>> questionAnswers = answerRepository.findAllByQuestionIds(questionIds)
                .stream()
                .collect(Collectors.groupingBy(answer -> questionIdQuestion.get(answer.getQuestionId())));

        return new ReviewsGatheredBySectionResponse(mapToResponseBySection(questionAnswers));
    }

    private void validateSectionId(long sectionId, ReviewGroup reviewGroup) {
        boolean existsByIdAndTemplateId = sectionRepository.existsByIdAndTemplateId(sectionId, reviewGroup.getTemplateId());
        if (!existsByIdAndTemplateId) {
            throw new SectionNotFoundInTemplateException(sectionId, reviewGroup.getTemplateId());
        }
    }

    private List<ReviewsGatheredByQuestionResponse> mapToResponseBySection(Map<Question, List<Answer>> questionsToAnswers) {
        return questionsToAnswers.entrySet().stream()
                .map(entry -> mapToResponseByQuestion(entry.getKey(), entry.getValue()))
                .toList();
    }

    private ReviewsGatheredByQuestionResponse mapToResponseByQuestion(Question question, List<Answer> answers) {
        return new ReviewsGatheredByQuestionResponse(
                new SimpleQuestionResponse(question.getContent(), question.getQuestionType()),
                mapToTextResponse(question, answers),
                mapToVoteResponse(question, answers)
        );
    }

    @Nullable
    List<TextResponse> mapToTextResponse(Question question, List<Answer> answers) {
        if (question.isSelectable()) {
            return null;
        }

        return answers.stream()
                .map(answer -> (TextAnswer) answer)
                .map(textAnswer -> new TextResponse(textAnswer.getContent()))
                .toList();
    }

    @Nullable
    List<VoteResponse> mapToVoteResponse(Question question, List<Answer> answers) {
        if (!question.isSelectable()) {
            return null;
        }

        Map<Long, Long> voteCountByOptionItemId = answers.stream()
                .map(answer -> (CheckboxAnswer) answer)
                .flatMap(checkboxAnswer -> checkboxAnswer.getSelectedOptionIds().stream())
                .collect(Collectors.groupingBy(CheckboxAnswerSelectedOption::getSelectedOptionId,
                        Collectors.counting()));

        List<OptionItem> allOptionItem = questionRepository.findAllOptionItemsById(question.getId());
        return allOptionItem.stream()
                .map(optionItem -> new VoteResponse(
                        optionItem.getContent(),
                        voteCountByOptionItemId.getOrDefault(optionItem.getId(), 0L)))
                .toList();
    }
}
