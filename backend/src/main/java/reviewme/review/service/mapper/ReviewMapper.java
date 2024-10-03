package reviewme.review.service.mapper;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final AnswerMapper answerMapper;
    private final TemplateCacheRepository templateCacheRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    public Review mapToReview(ReviewRegisterRequest request) {
        ReviewGroup reviewGroup = findReviewGroupByRequestCodeOrThrow(request.reviewRequestCode());
        Template template = templateCacheRepository.findTemplateById(reviewGroup.getTemplateId());

        List<TextAnswer> textAnswers = new ArrayList<>();
        List<CheckboxAnswer> checkboxAnswers = new ArrayList<>();
        addAnswersByQuestionType(request, textAnswers, checkboxAnswers);

        return new Review(template.getId(), reviewGroup.getId(), textAnswers, checkboxAnswers);
    }

    private ReviewGroup findReviewGroupByRequestCodeOrThrow(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }

    private void addAnswersByQuestionType(ReviewRegisterRequest request,
                                          List<TextAnswer> textAnswers, List<CheckboxAnswer> checkboxAnswers) {
        for (ReviewAnswerRequest answerRequest : request.answers()) {
            Question question = templateCacheRepository.findQuestionById(answerRequest.questionId());

            if (question.getQuestionType() == QuestionType.TEXT) {
                addIfTextAnswerExists(answerRequest, question, textAnswers);
            }

            if (question.getQuestionType() == QuestionType.CHECKBOX) {
                addIfCheckBoxAnswerExists(answerRequest, question, checkboxAnswers);
            }
        }
    }

    private void addIfTextAnswerExists(ReviewAnswerRequest answerRequest,
                                       Question question,
                                       List<TextAnswer> textAnswers) {
        if (question.isRequired() || answerRequest.hasTextAnswer()) {
            TextAnswer textAnswer = answerMapper.mapToTextAnswer(answerRequest);
            textAnswers.add(textAnswer);
        }
    }

    private void addIfCheckBoxAnswerExists(ReviewAnswerRequest answerRequest,
                                           Question question,
                                           List<CheckboxAnswer> checkboxAnswers) {
        if (question.isRequired() || answerRequest.hasCheckboxAnswer()) {
            CheckboxAnswer checkboxAnswer = answerMapper.mapToCheckBoxAnswer(answerRequest);
            checkboxAnswers.add(checkboxAnswer);
        }
    }
}
