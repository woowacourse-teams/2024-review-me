package reviewme.review.service;

import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionItemRepository;
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
public class GatheredReviewLookupService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final OptionItemRepository optionItemRepository;

    @Transactional(readOnly = true)
    public ReviewsGatheredBySectionResponse getReceivedReviewsBySectionId(String reviewRequestCode, long sectionId) {
        List<Question> questions = questionRepository
                .findAllByReviewRequestCodeAndSectionId(reviewRequestCode, sectionId);
        Map<Long, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));
        List<Answer> answers = answerRepository.findAllByQuestions(new ArrayList<>(questionMap.keySet()));

        Map<Question, List<Answer>> questionIdAnswers = questions.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        question -> answers.stream()
                                .filter(answer -> answer.getQuestionId() == question.getId())
                                .collect(Collectors.toList())
                ));
        return new ReviewsGatheredBySectionResponse(mapToResponse(questionIdAnswers));
    }

    private List<ReviewsGatheredByQuestionResponse> mapToResponse(Map<Question, List<Answer>> questionsToAnswers) {
        List<ReviewsGatheredByQuestionResponse> result = new ArrayList<>();
        for (Entry<Question, List<Answer>> questionAnswers : questionsToAnswers.entrySet()) {
            Question question = questionAnswers.getKey();
            result.add(new ReviewsGatheredByQuestionResponse(
                    new SimpleQuestionResponse(question.getContent(), question.getQuestionType()),
                    mapToTextResponse(question, questionsToAnswers.get(question)),
                    mapToVoteResponse(question, questionsToAnswers.get(question))
            ));
        }
        return result;
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

        List<OptionItem> allOptionItem = optionItemRepository.findAllByQuestionId(question.getId());
        return allOptionItem.stream()
                .map(optionItem -> new VoteResponse(
                        optionItem.getContent(),
                        voteCountByOptionItemId.getOrDefault(optionItem.getId(), 0L)))
                .toList();
    }
}
