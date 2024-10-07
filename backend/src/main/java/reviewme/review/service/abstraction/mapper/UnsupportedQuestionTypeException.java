package reviewme.review.service.abstraction.mapper;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;
import reviewme.question.domain.QuestionType;

@Slf4j
public class UnsupportedQuestionTypeException extends DataInconsistencyException {

    public UnsupportedQuestionTypeException(QuestionType questionType) {
        super("서버 내부 오류입니다. 관리자에게 문의해주세요.");
        log.error("Unsupported question type for mapping - questionType: {}", questionType);
    }
}
