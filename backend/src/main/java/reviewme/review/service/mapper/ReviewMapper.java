package reviewme.review.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;
import reviewme.template.repository.TemplateRepository;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final AnswerMapper answerMapper;
    private final ReviewGroupRepository reviewGroupRepository;
    private final QuestionRepository questionRepository;
    private final TemplateRepository templateRepository;

    public Review mapToReview(ReviewRegisterRequest request) {
        ReviewGroup reviewGroup = findReviewGroupByRequestCodeOrThrow(request.reviewRequestCode());
        Template template = findTemplateByReviewGroupOrThrow(reviewGroup);

        List<TextAnswer> textAnswers = new ArrayList<>();
        List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();
        addAnswersByQuestionType(request, textAnswers, checkboxAnswers);

        return new Review(template.getId(), reviewGroup.getId(), textAnswers, checkboxAnswers);
    }

    private ReviewGroup findReviewGroupByRequestCodeOrThrow(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }

    private Template findTemplateByReviewGroupOrThrow(ReviewGroup reviewGroup) {
        return templateRepository.findById(reviewGroup.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(
                        reviewGroup.getId(), reviewGroup.getTemplateId()));
    }

    private void addAnswersByQuestionType(ReviewRegisterRequest request,
                                          List<TextAnswer> textAnswers, List<CheckboxAnswer> checkboxAnswers) {
        List<Long> questionIds = request.answers()
                .stream()
                .map(ReviewAnswerRequest::questionId)
                .toList();

        Map<Long, Question> questionMap = questionRepository.findAllById(questionIds)
                .stream()
                .collect(Collectors.toMap(Question::getId, question -> question));

        for (ReviewAnswerRequest answerRequest : request.answers()) {
            Question question = questionMap.get(answerRequest.questionId());

            if (question.getQuestionType() == QuestionType.TEXT) {
                TextAnswer textAnswer = answerMapper.mapToTextAnswer(answerRequest);
                textAnswers.add(textAnswer);
            }

            if (question.getQuestionType() == QuestionType.CHECKBOX) {
                CheckboxAnswer checkboxAnswer = answerMapper.mapToCheckBoxAnswer(answerRequest);
                checkboxAnswers.add(checkboxAnswer);
            }
        }
    }
}
