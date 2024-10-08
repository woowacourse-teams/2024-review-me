package reviewme.review.service.mapper;

import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.Answer;
import reviewme.review.domain.Review;
import reviewme.review.service.dto.request.ReviewAnswerRequest;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.domain.repository.TemplateRepository;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewMapper {

    private final ReviewGroupRepository reviewGroupRepository;
    private final TemplateCacheRepository templateCacheRepository;
    private final AnswerMapperFactory answerMapperFactory;

    private final TemplateRepository templateRepository;

    public Review mapToReview(ReviewRegisterRequest request) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(request.reviewRequestCode())
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(request.reviewRequestCode()));
        Template template = templateRepository.findById(reviewGroup.getTemplateId());

        List<Answer> answers = getAnswersByQuestionType(request);
        return new Review(template.getId(), reviewGroup.getId(), answers);
    }

    private List<Answer> getAnswersByQuestionType(ReviewRegisterRequest request) {
        return request.answers()
                .stream()
                .map(this::mapRequestToAnswer)
                .filter(Objects::nonNull)
                .toList();
    }

    private Answer mapRequestToAnswer(ReviewAnswerRequest answerRequest) {
        Question question = templateCacheRepository.findQuestionById(answerRequest.questionId());
        AnswerMapper answerMapper = answerMapperFactory.getAnswerMapper(question.getQuestionType());
        return answerMapper.mapToAnswer(answerRequest);
    }
}
