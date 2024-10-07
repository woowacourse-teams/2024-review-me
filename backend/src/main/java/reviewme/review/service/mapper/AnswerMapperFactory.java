package reviewme.review.service.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.QuestionType;

@Component
@RequiredArgsConstructor
public class AnswerMapperFactory {

    private final List<AnswerMapper> answerMappers;

    public AnswerMapper getAnswerMapper(QuestionType questionType) {
        return answerMappers.stream()
                .filter(answerMapper -> answerMapper.supports(questionType))
                .findFirst()
                .orElseThrow(() -> new UnsupportedQuestionTypeException(questionType));
    }
}
