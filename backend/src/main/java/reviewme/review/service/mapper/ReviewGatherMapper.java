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
import reviewme.review.service.dto.response.gathered.ReviewsGatheredBySectionResponse;
import reviewme.review.service.dto.response.gathered.SimpleQuestionResponse;
import reviewme.review.service.dto.response.gathered.TextResponse;
import reviewme.review.service.dto.response.gathered.VoteResponse;
import reviewme.review.service.exception.GatheredAnswersTypeNonUniformException;

@Component
@RequiredArgsConstructor
public class ReviewGatherMapper {

    private final QuestionRepository questionRepository;

    public ReviewsGatheredBySectionResponse mapToReviewsGatheredBySection(Map<Question, List<Answer>> questionAnswers) {
        List<ReviewsGatheredByQuestionResponse> reviews = questionAnswers.entrySet().stream()
                .map(entry -> mapToReviewsGatheredByQuestion(entry.getKey(), entry.getValue()))
                .toList();

        return new ReviewsGatheredBySectionResponse(reviews);
    }

    private ReviewsGatheredByQuestionResponse mapToReviewsGatheredByQuestion(Question question, List<Answer> answers) {
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

        List<TextAnswer> textAnswers = castAllOrThrow(answers, TextAnswer.class);
        return textAnswers.stream()
                .map(textAnswer -> new TextResponse(textAnswer.getId(), textAnswer.getContent(), List.of()))
                .toList();
    }

    @Nullable
    private List<VoteResponse> mapToVoteResponse(Question question, List<Answer> answers) {
        if (!question.isSelectable()) {
            return null;
        }

        List<CheckboxAnswer> checkboxAnswers = castAllOrThrow(answers, CheckboxAnswer.class);
        Map<Long, Long> optionItemIdVoteCount = checkboxAnswers.stream()
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

    private <T extends Answer> List<T> castAllOrThrow(List<Answer> answers, Class<T> clazz) {
        try {
            return answers.stream().map(clazz::cast).toList();
        } catch (Exception ex) {
            throw new GatheredAnswersTypeNonUniformException(ex);
        }
    }
}
