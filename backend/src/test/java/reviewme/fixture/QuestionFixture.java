package reviewme.fixture;

import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;

public enum QuestionFixture {

    카테고리_질문_선택형(true, QuestionType.CHECKBOX, "카테고리 선택형", null, 1),
    꼬리_질문_선택형(true, QuestionType.CHECKBOX, "꼬리 질문 선택형", null, 1),
    꼬리_질문_서술형(true, QuestionType.TEXT, "꼬리 질문 서술형", null, 2),
    단점_보완_질문_서술형(true, QuestionType.TEXT, "단점 보완 질문 서술형", null, 1),
    응원_서술형(false, QuestionType.TEXT, "응원 질문 서술형", null, 1),
    ;

    private final boolean required;
    private final QuestionType type;
    private final String content;
    private final String guideLine;
    private final int position;

    QuestionFixture(boolean required, QuestionType type, String content, String guideLine, int position) {
        this.required = required;
        this.type = type;
        this.content = content;
        this.guideLine = guideLine;
        this.position = position;
    }

    public Question create() {
        return new Question(this.required, this.type, this.content, this.guideLine, this.position);
    }
    
    public Question createWithPosition(int position) {
        return new Question(this.required, this.type, this.content, this.guideLine, position);
    }
}
