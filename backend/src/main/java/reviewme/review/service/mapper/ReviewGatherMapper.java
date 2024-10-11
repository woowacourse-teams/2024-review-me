package reviewme.review.service.mapper;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredByQuestionResponse;
import reviewme.review.service.dto.response.gathered.SimpleQuestionResponse;
import reviewme.review.service.dto.response.gathered.TextResponse;
import reviewme.review.service.dto.response.gathered.VoteResponse;

@Component
@RequiredArgsConstructor
public class ReviewGatherMapper {

    private final QuestionRepository questionRepository;

    public List<ReviewsGatheredByQuestionResponse> mapToResponseBySection(
            Map<Question, List<Answer>> questionsToAnswers) {
        return questionsToAnswers.entrySet().stream()
                .map(entry -> mapToResponseByQuestion(entry.getKey(), entry.getValue()))
                .toList();
    }

    private ReviewsGatheredByQuestionResponse mapToResponseByQuestion(Question question, List<Answer> answers) {
        return new ReviewsGatheredByQuestionResponse(
                new SimpleQuestionResponse(question.getId(), question.getContent(), question.getQuestionType()),
                mapToTextResponse(question, answers),
                mapToVoteResponse(question, answers)
        );
    }

    @Nullable
    private List<TextResponse> mapToTextResponse(Question question, List<Answer> answers) {
        if (question.isSelectable()) {
            return null;
        }

        return answers.stream()
                .map(answer -> (TextAnswer) answer)
                .map(textAnswer -> new TextResponse(textAnswer.getId(), textAnswer.getContent(), List.of()))
                .toList();
    }

    @Nullable
    private List<VoteResponse> mapToVoteResponse(Question question, List<Answer> answers) {
        if (!question.isSelectable()) {
            return null;
        }

        Map<Long, Long> optionItemIdVoteCount = answers.stream()
                .map(answer -> (CheckboxAnswer) answer)
                .flatMap(checkboxAnswer -> checkboxAnswer.getSelectedOptionIds().stream())
                .collect(Collectors.groupingBy(CheckboxAnswerSelectedOption::getSelectedOptionId,
                        Collectors.counting()));

        List<OptionItem> allOptionItem = questionRepository.findAllOptionItemsById(question.getId());
        return allOptionItem.stream()
                .map(optionItem -> new VoteResponse(
                        optionItem.getContent(),
                        optionItemIdVoteCount.getOrDefault(optionItem.getId(), 0L)))
                .toList();
    }
}
