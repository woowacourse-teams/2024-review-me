package reviewme.fixture;


import java.util.List;
import reviewme.template.domain.Section;
import reviewme.template.domain.VisibleType;

public enum SectionFixture {

    카테고리_섹션(VisibleType.ALWAYS, null, "카테고리 섹션", 1),
    꼬리_질문_섹션(VisibleType.CONDITIONAL, 1L, "꼬리질문 섹션", 2),
    단점_보완_섹션(VisibleType.ALWAYS, null, "단점 보완 섹션", 3),
    응원_섹션(VisibleType.ALWAYS, null, "응원 섹션", 4);

    private final VisibleType visibleType;
    private final Long onSelectedOptionId;
    private final String header;
    private final int position;

    SectionFixture(VisibleType visibleType, Long onSelectedOptionId, String header, int position) {
        this.visibleType = visibleType;
        this.onSelectedOptionId = onSelectedOptionId;
        this.header = header;
        this.position = position;
    }

    public Section createWithQuestionIds(List<Long> questionIds) {
        return new Section(this.visibleType, questionIds, this.onSelectedOptionId, this.header, this.position);
    }

    public Section createWithQuestionIdsAndOnSelectedOptionId(List<Long> questionIds, Long onSelectedOptionId) {
        return new Section(this.visibleType, questionIds, onSelectedOptionId, this.header, this.position);
    }
}
