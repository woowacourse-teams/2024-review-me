package reviewme.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review2;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.ReviewGroupNotFoundByRequestReviewCodeException;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.dto.request.create.CreateReviewRequest2;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.repository.Review2Repository;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@Service
@RequiredArgsConstructor
public class CreateReviewService {

    private final Review2Repository review2Repository;
    private final QuestionRepository2 questionRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final TemplateRepository templateRepository;
    private final SectionRepository sectionRepository;
    private final CreateTextAnswerRequestValidator createTextAnswerRequestValidator;
    private final CreateCheckBoxAnswerRequestValidator createCheckBoxAnswerRequestValidator;

    @Transactional
    public long createReview(CreateReviewRequest2 request) {
        ReviewGroup reviewGroup = validateReviewGroupByRequestCode(request.reviewRequestCode());
        validateSubmittedQuestionsContainingInTemplate(reviewGroup.getTemplateId(), request);
        return saveReview(request, reviewGroup);
    }

    private ReviewGroup validateReviewGroupByRequestCode(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByRequestReviewCodeException(reviewRequestCode));
    }

    private void validateSubmittedQuestionsContainingInTemplate(long templateId, CreateReviewRequest2 request) {
        Set<Long> providedQuestionIds = questionRepository.findAllQuestionIdByTemplateId(templateId);
        Set<Long> submittedQuestionIds = request.answers()
                .stream()
                .map(CreateReviewAnswerRequest::questionId)
                .collect(Collectors.toSet());
        if (!providedQuestionIds.containsAll(submittedQuestionIds)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(submittedQuestionIds, providedQuestionIds);
        }
    }

    private Long saveReview(CreateReviewRequest2 request, ReviewGroup reviewGroup) {
        List<TextAnswer> textAnswers = new ArrayList<>();
        List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();
        for (CreateReviewAnswerRequest answerRequests : request.answers()) {
            Question2 question = questionRepository.getQuestionById(answerRequests.questionId());
            QuestionType questionType = question.getQuestionType();
            if (questionType == QuestionType.TEXT) {
                createTextAnswerRequestValidator.validate(answerRequests);
                textAnswers.add(new TextAnswer(question.getId(), answerRequests.text()));
                continue;
            }
            if (questionType == QuestionType.CHECKBOX) {
                createCheckBoxAnswerRequestValidator.validate(answerRequests);
                checkboxAnswers.add(new CheckboxAnswer(question.getId(), answerRequests.selectedOptionIds()));
            }
        }

        Review2 savedReview = review2Repository.save(
                new Review2(reviewGroup.getTemplateId(), reviewGroup.getId(), textAnswers, checkboxAnswers)
        );
        return savedReview.getId();
    }
}
