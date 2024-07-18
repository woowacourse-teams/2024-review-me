package reviewme.keyword.domain;

import java.util.Collections;
import java.util.List;
import reviewme.keyword.domain.exception.DuplicatedKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;

public class Keywords {

    private static final int MAX_KEYWORD_COUNT = 3;

    private final List<Keyword> selectedKeywords;

    public Keywords(List<Keyword> keywords) {
        if (keywords.size() > MAX_KEYWORD_COUNT) {
            throw new KeywordLimitExceedException(MAX_KEYWORD_COUNT);
        }
        if (hasDuplicatedKeywords(keywords)) {
            throw new DuplicatedKeywordException();
        }
        this.selectedKeywords = keywords;
    }

    private boolean hasDuplicatedKeywords(List<Keyword> keywords) {
        long distinctKeywordCount = keywords.stream()
                .map(Keyword::getDetail)
                .distinct()
                .count();
        return keywords.size() != distinctKeywordCount;
    }

    public List<Keyword> getSelectedKeywords() {
        return Collections.unmodifiableList(selectedKeywords);
    }
}
