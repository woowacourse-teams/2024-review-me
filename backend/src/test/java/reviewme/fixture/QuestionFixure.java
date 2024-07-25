package reviewme.fixture;

import reviewme.review.domain.Question;

public enum QuestionFixure {

    소프트스킬이_어떤가요,
    기술역량이_어떤가요,
    ;

    public Question create() {
        return new Question(replaceUnderscores());
    }

    private String replaceUnderscores() {
        return name().replace("_", " ");
    }
}
