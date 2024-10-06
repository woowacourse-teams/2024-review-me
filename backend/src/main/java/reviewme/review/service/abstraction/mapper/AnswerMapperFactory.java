package reviewme.review.service.abstraction.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.QuestionType;

@Component
@RequiredArgsConstructor
public class AnswerMapperFactory {

    private final List<NewAnswerMapper> answerMappers;

    public NewAnswerMapper getAnswerMapper(QuestionType questionType) {
        return answerMappers.stream()
                .filter(answerMapper -> answerMapper.supports(questionType))
                .findFirst()
                .orElseThrow(() -> new UnsupportedQuestionTypeException(questionType));
    }
}
