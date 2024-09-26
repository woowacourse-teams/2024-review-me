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
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;

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
