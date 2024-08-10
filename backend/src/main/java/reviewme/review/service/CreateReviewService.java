package reviewme.review.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.Question2Repository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review2;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.ReviewGroupNotFoundByRequestReviewCodeException;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.dto.request.create.CreateReviewRequest;
import reviewme.review.repository.Review2Repository;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@Service
@RequiredArgsConstructor
public class CreateReviewService {

    private final Review2Repository review2Repository;
    private final Question2Repository question2Repository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final TemplateRepository templateRepository;
    private final SectionRepository sectionRepository;
    private final CreateTextAnswerRequestValidator createTextAnswerRequestValidator;
    private final CreateCheckBoxAnswerRequestValidator createCheckBoxAnswerRequestValidator;

    public long createReview(CreateReviewRequest request) {
        ReviewGroup reviewGroup = validateReviewGroupByRequestCode(request.reviewRequestCode());
        Template template = templateRepository.getTemplateById(reviewGroup.getTemplateId());
        validateSubmittedQuestionAndProvidedQuestionMatch(request, template);
        return saveReview(request, reviewGroup);
    }

    private ReviewGroup validateReviewGroupByRequestCode(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByRequestReviewCodeException(reviewRequestCode));
    }

    private void validateSubmittedQuestionAndProvidedQuestionMatch(CreateReviewRequest request, Template template) {
        List<Long> providedQuestionIds = template.getSectionIds()
                .stream()
                .map(sectionRepository::getSectionById)
                .flatMap(section -> section.getQuestionIds().stream())
                .toList();
        List<Long> submittedQuestionIds = request.answers()
                .stream()
                .map(CreateReviewAnswerRequest::questionId)
                .toList();

        if (!new HashSet<>(providedQuestionIds).containsAll(submittedQuestionIds)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(submittedQuestionIds, providedQuestionIds);
        }
    }

    private Long saveReview(CreateReviewRequest request, ReviewGroup reviewGroup) {
        List<TextAnswer> textAnswers = new ArrayList<>();
        List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();
        for (CreateReviewAnswerRequest answerRequests : request.answers()) {
            Question2 question = question2Repository.getQuestionById(answerRequests.questionId());
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
