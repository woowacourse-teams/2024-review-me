package reviewme.review.service;

import java.util.ArrayList;
import java.util.HashSet;
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
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.review.service.exception.UnnecessaryQuestionIncludedException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.SectionQuestion;
import reviewme.template.domain.Template;
import reviewme.template.domain.exception.TemplateNotFoundByReviewGroupException;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@Service
@RequiredArgsConstructor
public class CreateReviewService {

    private final ReviewRepository reviewRepository;
    private final QuestionRepository questionRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final CreateTextAnswerRequestValidator createTextAnswerRequestValidator;
    private final CreateCheckBoxAnswerRequestValidator createCheckBoxAnswerRequestValidator;
    private final TemplateRepository templateRepository;
    private final SectionRepository sectionRepository;

    @Transactional
    public long createReview(CreateReviewRequest request) {
        ReviewGroup reviewGroup = validateReviewGroupByRequestCode(request.reviewRequestCode());
        Template template = templateRepository.findById(reviewGroup.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(
                        reviewGroup.getId(), reviewGroup.getTemplateId()));
        validateSubmittedQuestionsContainedInTemplate(reviewGroup.getTemplateId(), request);
        validateOnlyRequiredQuestionsSubmitted(template, request);

        return saveReview(request, reviewGroup);
    }

    private ReviewGroup validateReviewGroupByRequestCode(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }

    private void validateSubmittedQuestionsContainedInTemplate(long templateId, CreateReviewRequest request) {
        Set<Long> providedQuestionIds = questionRepository.findAllQuestionIdByTemplateId(templateId);
        Set<Long> submittedQuestionIds = request.answers()
                .stream()
                .map(CreateReviewAnswerRequest::questionId)
                .collect(Collectors.toSet());
        if (!providedQuestionIds.containsAll(submittedQuestionIds)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(submittedQuestionIds, providedQuestionIds);
        }
    }

    private void validateOnlyRequiredQuestionsSubmitted(Template template, CreateReviewRequest request) {
        // 제출된 리뷰의 옵션 아이템 ID 목록
        List<Long> selectedOptionItemIds = request.answers()
                .stream()
                .filter(answer -> answer.selectedOptionIds() != null)
                .flatMap(answer -> answer.selectedOptionIds().stream())
                .toList();

        // 제출된 리뷰의 질문 ID 목록
        List<Long> submittedQuestionIds = request.answers()
                .stream()
                .map(CreateReviewAnswerRequest::questionId)
                .toList();

        // 섹션에서 답해야 할 질문 ID 목록
        List<Long> requiredQuestionIdsCandidates = sectionRepository.findAllByTemplateId(template.getId())
                .stream()
                // 선택된 optionItem 에 따라 required 를 다르게 책정해서 필터링
                .filter(section -> section.isVisibleBySelectedOptionIds(selectedOptionItemIds))
                .flatMap(section -> section.getQuestionIds().stream())
                .map(SectionQuestion::getQuestionId)
                .toList();
        List<Long> requiredQuestionIds = questionRepository.findAllById(requiredQuestionIdsCandidates)
                .stream()
                .filter(Question::isRequired)
                .map(Question::getId)
                .toList();

        // 제출된 리뷰의 질문 중에서 제출해야 할 질문이 모두 포함되었는지 검사
        Set<Long> submittedQuestionIds2 = new HashSet<>(submittedQuestionIds);
        if (!submittedQuestionIds2.containsAll(requiredQuestionIds)) {
            List<Long> missingRequiredQuestionIds = new ArrayList<>(requiredQuestionIds);
            missingRequiredQuestionIds.removeAll(submittedQuestionIds2);
            throw new MissingRequiredQuestionException(missingRequiredQuestionIds);
        }

        // 제출된 리뷰의 질문 중에서 필수가 아닌 질문이 포함되었는지 검사
        requiredQuestionIds.forEach(submittedQuestionIds2::remove);
        List<Long> unnecessaryQuestionIds = questionRepository.findAllById(submittedQuestionIds2)
                .stream()
                .filter(Question::isRequired)
                .map(Question::getId)
                .toList();
        if (!unnecessaryQuestionIds.isEmpty()) {
            throw new UnnecessaryQuestionIncludedException(unnecessaryQuestionIds);
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
