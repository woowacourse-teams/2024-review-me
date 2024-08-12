package reviewme.fixture;

import java.util.List;
import reviewme.template.domain.Section;
import reviewme.template.domain.VisibleType;

public enum SectionFixture {

    카테고리_섹션(VisibleType.ALWAYS, null, "카테고리 섹션", 1),
    꼬리_질문_섹션(VisibleType.CONDITIONAL, 1L, "꼬리질문 섹션", 2),
    응원_섹션(VisibleType.ALWAYS, null, "응원 섹션", 3);

    private VisibleType visibleType;
    private Long onSelectedOptionId;
    private String header;
    private int position;

    SectionFixture(VisibleType visibleType, Long onSelectedOptionId, String header, int position) {
        this.visibleType = visibleType;
        this.onSelectedOptionId = onSelectedOptionId;
        this.header = header;
        this.position = position;
    }

    public Section create(List<Long> questionIds) {
        return new Section(visibleType, questionIds, onSelectedOptionId, header, position);
    }
}
