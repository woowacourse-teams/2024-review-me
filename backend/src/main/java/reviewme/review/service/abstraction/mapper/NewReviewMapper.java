package reviewme.review.service.abstraction.mapper;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.abstraction.Answer;
import reviewme.review.domain.abstraction.NewReview;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NewReviewMapper {

    private final ReviewGroupRepository reviewGroupRepository;
    private final TemplateCacheRepository templateCacheRepository;
    private final AnswerMapperFactory answerMapperFactory;

    public NewReview mapToReview(ReviewRegisterRequest request) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(request.reviewRequestCode())
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(request.reviewRequestCode()));
        Template template = templateCacheRepository.findTemplateById(reviewGroup.getTemplateId());

        List<Answer> answers = getAnswersByQuestionType(request);
        return new NewReview(template.getId(), reviewGroup.getId(), answers);
    }

    private List<Answer> getAnswersByQuestionType(ReviewRegisterRequest request) {
        return request.answers()
                .stream()
                .map(this::mapRequestToAnswer)
                .toList();
    }

    private Answer mapRequestToAnswer(ReviewAnswerRequest answerRequest) {
        Question question = templateCacheRepository.findQuestionById(answerRequest.questionId());
        NewAnswerMapper answerMapper = answerMapperFactory.getAnswerMapper(question.getQuestionType());
        return answerMapper.mapToAnswer(answerRequest);
    }
}
