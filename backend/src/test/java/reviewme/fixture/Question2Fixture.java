package reviewme.fixture;

import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;

public class Question2Fixture {

    public static final Question2 카테고리_질문 = new Question2(true, QuestionType.CHECKBOX, "카테고리 선택형", null, 1);
    public static final Question2 선택형_꼬리_질문 = new Question2(true, QuestionType.CHECKBOX, "꼬리 질문 선택형", null, 1);
    public static final Question2 서술형_꼬리_질문 = new Question2(true, QuestionType.TEXT, "꼬리 질문 서술형", null, 1);
}
