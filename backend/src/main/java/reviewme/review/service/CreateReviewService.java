package reviewme.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.dto.request.CreateReviewRequest;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class CreateReviewService {

    private final ReviewRepository reviewRepository;
    private final QuestionRepository questionRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final CreateTextAnswerRequestValidator createTextAnswerRequestValidator;
    private final CreateCheckBoxAnswerRequestValidator createCheckBoxAnswerRequestValidator;

    @Transactional
    public long createReview(CreateReviewRequest request) {
        ReviewGroup reviewGroup = validateReviewGroupByRequestCode(request.reviewRequestCode());
        validateSubmittedQuestionsContainingInTemplate(reviewGroup.getTemplateId(), request);
        return saveReview(request, reviewGroup);
    }

    private ReviewGroup validateReviewGroupByRequestCode(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }

    private void validateSubmittedQuestionsContainingInTemplate(long templateId, CreateReviewRequest request) {
        Set<Long> providedQuestionIds = questionRepository.findAllQuestionIdByTemplateId(templateId);
        Set<Long> submittedQuestionIds = request.answers()
                .stream()
                .map(CreateReviewAnswerRequest::questionId)
                .collect(Collectors.toSet());
        if (!providedQuestionIds.containsAll(submittedQuestionIds)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(submittedQuestionIds, providedQuestionIds);
        }
    }

    private Long saveReview(CreateReviewRequest request, ReviewGroup reviewGroup) {
        List<TextAnswer> textAnswers = new ArrayList<>();
        List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();
        for (CreateReviewAnswerRequest answerRequests : request.answers()) {
            Question question = questionRepository.findById(answerRequests.questionId())
                    .orElseThrow(() -> new SubmittedQuestionNotFoundException(answerRequests.questionId()));
            QuestionType questionType = question.getQuestionType();
            if (questionType == QuestionType.TEXT && answerRequests.isNotBlank()) {
                createTextAnswerRequestValidator.validate(answerRequests);
                textAnswers.add(new TextAnswer(question.getId(), answerRequests.text()));
                continue;
            }
            if (questionType == QuestionType.CHECKBOX) {
                createCheckBoxAnswerRequestValidator.validate(answerRequests);
                checkboxAnswers.add(new CheckboxAnswer(question.getId(), answerRequests.selectedOptionIds()));
            }
        }

        Review savedReview = reviewRepository.save(
                new Review(reviewGroup.getTemplateId(), reviewGroup.getId(), textAnswers, checkboxAnswers)
        );
        return savedReview.getId();
    }
}
