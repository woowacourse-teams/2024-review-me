package reviewme.fixture;

import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;

public enum Question2Fixture {

    객관식_카테고리_질문(true, QuestionType.CHECKBOX, "카테고리", null, 1),
    객관식_꼬리_질문(true, QuestionType.CHECKBOX, "객관식 꼬리", null, 1),
    주관식_꼬리_질문(true, QuestionType.TEXT, "주관식 꼬리", null, 2),
    주관식_필수_아닌_질문(false, QuestionType.TEXT, "주관식 응원", null, 1),
    ;

    private boolean required;
    private QuestionType type;
    private String content;
    private String guideLine;
    private int order;

    public Question2 create() {
        return new Question2(required, type, content, guideLine, order);
    }

    Question2Fixture(boolean required, QuestionType type, String content, String guideLine, int order) {
        this.required = required;
        this.type = type;
        this.content = content;
        this.guideLine = guideLine;
        this.order = order;
    }
}
