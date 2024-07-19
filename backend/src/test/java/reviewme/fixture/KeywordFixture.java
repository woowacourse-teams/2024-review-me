package reviewme.fixture;

import reviewme.keyword.domain.Keyword;

public enum KeywordFixture {

    추진력이_좋아요,
    회의를_이끌어요,
    의견을_잘_조율해요,
    꼼꼼하게_기록해요,
    ;

    public Keyword create() {
        return new Keyword(replaceUnderscore());
    }

    private String replaceUnderscore() {
        return name().replace("_", " ");
    }
}
