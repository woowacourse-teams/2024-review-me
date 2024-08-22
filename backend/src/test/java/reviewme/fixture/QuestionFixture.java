package reviewme.fixture;

import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;

public class QuestionFixture {

    public Question 필수_선택형_질문() {
        return this.필수_선택형_질문(1);
    }

    public Question 필수_선택형_질문(int position) {
        return new Question(true, QuestionType.CHECKBOX, "본문", null, position);
    }

    public Question 필수_서술형_질문() {
        return this.필수_서술형_질문(1);
    }

    public Question 필수_서술형_질문(int position) {
        return new Question(true, QuestionType.TEXT, "본문", null, position);
    }

    public Question 필수_아닌_선택형_질문() {
        return this.필수_아닌_선택형_질문(1);
    }

    public Question 필수_아닌_선택형_질문(int position) {
        return new Question(false, QuestionType.CHECKBOX, "본문", null, position);
    }

    public Question 필수_아닌_서술형_질문() {
        return this.필수_아닌_서술형_질문(1);
    }

    public Question 필수_아닌_서술형_질문(int position) {
        return new Question(false, QuestionType.TEXT, "본문", null, position);
    }
}
