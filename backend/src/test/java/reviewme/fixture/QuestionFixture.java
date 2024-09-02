package reviewme.fixture;

import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;

@Component
public class QuestionFixture {

    public static Question 선택형_필수_질문() {
        return 선택형_필수_질문(1);
    }

    public static Question 선택형_필수_질문(int position) {
        return new Question(true, QuestionType.CHECKBOX, "본문", null, position);
    }

    public static Question 선택형_옵션_질문() {
        return 선택형_옵션_질문(1);
    }

    public static Question 선택형_옵션_질문(int position) {
        return new Question(false, QuestionType.CHECKBOX, "본문", null, position);
    }

    public static Question 서술형_필수_질문() {
        return 서술형_필수_질문(1);
    }

    public static Question 서술형_필수_질문(int position) {
        return new Question(true, QuestionType.TEXT, "본문", null, position);
    }

    public static Question 서술형_옵션_질문() {
        return 서술형_옵션_질문(1);
    }

    public static Question 서술형_옵션_질문(int position) {
        return new Question(false, QuestionType.TEXT, "본문", null, position);
    }
}
