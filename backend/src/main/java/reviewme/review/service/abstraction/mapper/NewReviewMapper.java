package reviewme.review.service.abstraction.mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.abstraction.Answer;
import reviewme.review.domain.abstraction.NewReview;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NewReviewMapper {

    private final AnswerMapperFactory answerMapperFactory;
    private final ReviewGroupRepository reviewGroupRepository;
    private final QuestionRepository questionRepository;
    private final TemplateRepository templateRepository;

    public NewReview mapToReview(ReviewRegisterRequest request) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(request.reviewRequestCode())
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(request.reviewRequestCode()));
        Template template = templateRepository.findById(reviewGroup.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(
                        reviewGroup.getId(), reviewGroup.getTemplateId()
                ));

        List<Answer> answers = getAnswersByQuestionType(request);
        return new NewReview(template.getId(), reviewGroup.getId(), answers);
    }

    private List<Answer> getAnswersByQuestionType(ReviewRegisterRequest request) {
        List<Long> questionIds = request.answers()
                .stream()
                .map(ReviewAnswerRequest::questionId)
                .toList();

        Map<Long, Question> questionMap = questionRepository.findAllById(questionIds)
                .stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        return request.answers()
                .stream()
                .map(answerRequest -> mapRequestToAnswer(questionMap, answerRequest))
                .filter(Objects::nonNull)
                .toList();
    }

    private Answer mapRequestToAnswer(Map<Long, Question> questions, ReviewAnswerRequest answerRequest) {
        Question question = questions.get(answerRequest.questionId());

        // TODO: 아래 코드를 삭제해야 한다
        if (question.isSelectable() && answerRequest.selectedOptionIds() != null && answerRequest.selectedOptionIds().isEmpty()) {
            return null;
        }
        if (!question.isSelectable() && answerRequest.text() != null && answerRequest.text().isEmpty()) {
            return null;
        }
        // END

        NewAnswerMapper answerMapper = answerMapperFactory.getAnswerMapper(question.getQuestionType());
        return answerMapper.mapToAnswer(answerRequest);
    }
}
