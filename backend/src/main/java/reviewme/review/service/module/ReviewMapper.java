package reviewme.review.service.module;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.domain.exception.TemplateNotFoundByReviewGroupException;
import reviewme.template.repository.TemplateRepository;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final ReviewGroupRepository reviewGroupRepository;
    private final TemplateRepository templateRepository;
    private final QuestionRepository questionRepository;

    public Review mapToReview(ReviewRegisterRequest request,
                              TextAnswerValidator textAnswerValidator,
                              CheckBoxAnswerValidator checkBoxAnswerValidator
    ) {
        ReviewGroup reviewGroup = findReviewGroupByRequestCodeOrThrow(request.reviewRequestCode());
        Template template = findTemplateByReviewGroupOrThrow(reviewGroup);


        List<TextAnswer> textAnswers = new ArrayList<>();
        List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();
        for (ReviewAnswerRequest answerRequest : request.answers()) {
            Question question = questionRepository.findById(answerRequest.questionId())
                    .orElseThrow(() -> new SubmittedQuestionNotFoundException(answerRequest.questionId()));

            if (question.getQuestionType() == QuestionType.TEXT) {
                TextAnswer textAnswer = mapToTextAnswer(answerRequest);
                textAnswerValidator.validate(textAnswer);
                textAnswers.add(textAnswer);
            }

            if (question.getQuestionType() == QuestionType.CHECKBOX) {
                CheckboxAnswer checkboxAnswer = mapToCheckboxAnswer(answerRequest);
                checkBoxAnswerValidator.validate(checkboxAnswer);
                checkboxAnswers.add(checkboxAnswer);
            }
        }

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

    private TextAnswer mapToTextAnswer(ReviewAnswerRequest answerRequest) {
        return new TextAnswer(answerRequest.questionId(), answerRequest.text());
    }

    private CheckboxAnswer mapToCheckboxAnswer(ReviewAnswerRequest answerRequest) {
        return new CheckboxAnswer(answerRequest.questionId(), answerRequest.selectedOptionIds());
    }
}
