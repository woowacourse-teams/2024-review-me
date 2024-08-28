package reviewme.fixture;

import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;

public class QuestionFixture {

    public Question 선택형_필수_질문() {
        return this.선택형_필수_질문(1);
    }

    public Question 선택형_필수_질문(int position) {
        return new Question(true, QuestionType.CHECKBOX, "본문", null, position);
    }

    public Question 선택형_옵션_질문() {
        return this.선택형_옵션_질문(1);
    }

    public Question 선택형_옵션_질문(int position) {
        return new Question(false, QuestionType.CHECKBOX, "본문", null, position);
    }

    public Question 서술형_필수_질문() {
        return this.서술형_필수_질문(1);
    }

    public Question 서술형_필수_질문(int position) {
        return new Question(true, QuestionType.TEXT, "본문", null, position);
    }

    public Question 서술형_옵션_질문() {
        return this.서술형_옵션_질문(1);
    }

    public Question 서술형_옵션_질문(int position) {
        return new Question(false, QuestionType.TEXT, "본문", null, position);
    }
}
