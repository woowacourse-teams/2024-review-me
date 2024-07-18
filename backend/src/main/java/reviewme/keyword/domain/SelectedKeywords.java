package reviewme.keyword.domain;

import java.util.Collections;
import java.util.List;
import reviewme.keyword.domain.exception.DuplicatedKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;

public class SelectedKeywords {

    private static final int MAX_KEYWORD_COUNT = 3;

    private final List<Keyword> keywords;

    public SelectedKeywords(List<Keyword> selectedKeywords) {
        if (selectedKeywords.size() > MAX_KEYWORD_COUNT) {
            throw new KeywordLimitExceedException(MAX_KEYWORD_COUNT);
        }
        if (hasDuplicatedKeywords(selectedKeywords)) {
            throw new DuplicatedKeywordException();
        }
        this.keywords = selectedKeywords;
    }

    private boolean hasDuplicatedKeywords(List<Keyword> selectedKeywords) {
        long distinctKeywordCount = selectedKeywords.stream()
                .map(Keyword::getDetail)
                .distinct()
                .count();
        return selectedKeywords.size() != distinctKeywordCount;
    }

    public List<Keyword> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }
}
