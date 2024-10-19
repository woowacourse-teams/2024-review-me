package reviewme.review.service.mapper;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.repository.HighlightRepository;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.response.gathered.HighlightResponse;
import reviewme.review.service.dto.response.gathered.RangeResponse;
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

    public ReviewsGatheredBySectionResponse mapToReviewsGatheredBySection(Map<Question, List<Answer>> questionAnswers,
                                                                          List<Highlight> highlights) {
        List<ReviewsGatheredByQuestionResponse> reviews = questionAnswers.entrySet()
                .stream()
                .map(entry -> mapToReviewsGatheredByQuestion(entry.getKey(), entry.getValue(), highlights))
                .toList();

        return new ReviewsGatheredBySectionResponse(reviews);
    }

    private ReviewsGatheredByQuestionResponse mapToReviewsGatheredByQuestion(Question question, List<Answer> answers,
                                                                             List<Highlight> highlights) {
        return new ReviewsGatheredByQuestionResponse(
                new SimpleQuestionResponse(question.getId(), question.getContent(), question.getQuestionType()),
                mapToTextResponse(question, answers, highlights),
                mapToVoteResponse(question, answers)
        );
    }

    @Nullable
    private List<TextResponse> mapToTextResponse(Question question, List<Answer> answers,
                                                 List<Highlight> highlights) {
        if (question.isSelectable()) {
            return null;
        }
        Map<Long, List<Highlight>> answerIdHighlights = highlights.stream()
                .collect(Collectors.groupingBy(Highlight::getAnswerId));

        List<TextAnswer> textAnswers = castAllOrThrow(answers, TextAnswer.class);
        return textAnswers.stream()
                .map(textAnswer -> new TextResponse(
                        textAnswer.getId(), textAnswer.getContent(),
                        mapToHighlightResponse(answerIdHighlights.getOrDefault(textAnswer.getId(), List.of())))
                ).toList();
    }

    private List<HighlightResponse> mapToHighlightResponse(List<Highlight> highlights) {
        // Line index를 기준으로 묶되, 묶은 것들은 mapping 함수를 통해 List로 변환
        Collector<Highlight, ?, Map<Integer, List<RangeResponse>>> highlightMapCollector = Collectors.groupingBy(
                Highlight::getLineIndex,
                Collectors.mapping(highlight -> RangeResponse.from(highlight.getHighlightRange()), Collectors.toList())
        );
        Map<Integer, List<RangeResponse>> lineIndexRangeResponses = highlights.stream()
                .collect(highlightMapCollector);

        return lineIndexRangeResponses.entrySet()
                .stream()
                .map(entry -> new HighlightResponse(entry.getKey(), entry.getValue()))
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

        List<OptionItem> allOptionItem = questionRepository.findAllOptionItemsByIdOrderByPosition(question.getId());
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
