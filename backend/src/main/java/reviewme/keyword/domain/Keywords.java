package reviewme.keyword.domain;

import java.util.Collections;
import java.util.List;
import reviewme.keyword.domain.exception.DuplicateKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;

public class Keywords {

    private static final int MAX_KEYWORD_COUNT = 5;

    private final List<Keyword> keywords;

    public Keywords(List<Keyword> selectedKeywords) {
        if (selectedKeywords.size() > MAX_KEYWORD_COUNT) {
            throw new KeywordLimitExceedException(MAX_KEYWORD_COUNT);
        }
        if (hasDuplicateKeywords(selectedKeywords)) {
            throw new DuplicateKeywordException();
        }
        this.keywords = selectedKeywords;
    }

    private boolean hasDuplicateKeywords(List<Keyword> selectedKeywords) {
        long distinctKeywordCount = selectedKeywords.stream()
                .distinct()
                .count();
        return selectedKeywords.size() != distinctKeywordCount;
    }

    public List<Keyword> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }
}
