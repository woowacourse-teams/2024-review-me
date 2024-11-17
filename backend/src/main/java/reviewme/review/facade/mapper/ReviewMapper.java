package reviewme.review.facade.mapper;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Answer;
import reviewme.review.domain.Review;
import reviewme.review.facade.request.ReviewAnswerRequest;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Question;
import reviewme.template.domain.StructuredTemplate;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewMapper {

    private final AnswerMapperFactory answerMapperFactory;

    public Review mapToReview(List<ReviewAnswerRequest> answerRequests, StructuredTemplate template,
                              ReviewGroup reviewGroup) {
        List<Answer> answers = mapAnswers(answerRequests, template);
        return new Review(template.getTemplateId(), reviewGroup.getId(), answers);
    }

    private List<Answer> mapAnswers(List<ReviewAnswerRequest> answerRequests, StructuredTemplate template) {
        List<Answer> answers = new ArrayList<>();
        for (ReviewAnswerRequest answerRequest : answerRequests) {
            Long questionId = answerRequest.questionId();
            Question question = template.getQuestions()
                    .stream()
                    .filter(q -> q.getId() == questionId)
                    .findFirst()
                    .orElseThrow(() -> new SubmittedQuestionNotFoundException(questionId));

            AnswerMapper answerMapper = answerMapperFactory.getAnswerMapper(question.getQuestionType());
            answers.add(answerMapper.mapToAnswer(answerRequest));
        }

        return answers;
    }
}
