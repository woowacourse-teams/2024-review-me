package reviewme.template.domain.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class StructuredTemplateCheckBoxQuestionFoundException extends BadRequestException {

    public StructuredTemplateCheckBoxQuestionFoundException(List<Long> questionIds) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("CheckBox Type Question Found during Only Text Answer StructuredTemplate creation: questionIds: {}",
                questionIds);
    }
}
