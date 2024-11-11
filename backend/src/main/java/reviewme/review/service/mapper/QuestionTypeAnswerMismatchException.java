package reviewme.review.service.mapper;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class QuestionTypeAnswerMismatchException extends BadRequestException {

    public QuestionTypeAnswerMismatchException(long questionId) {
        super("질문의 타입과 답변의 타입이 일치하지 않습니다.");
        log.info("Question type and answer type mismatch - questionId: {}", questionId);
        // todo: 맥락이 부족, 요청 내용도 기록하도록 로깅 보강 필요
    }
}
