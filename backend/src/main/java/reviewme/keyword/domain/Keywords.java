package reviewme.keyword.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.keyword.domain.exception.DuplicateKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Keywords {

    private static final int MAX_KEYWORD_COUNT = 5;

    @ElementCollection
    @CollectionTable(name = "review_keyword", joinColumns = @JoinColumn(name = "review_id"))
    private Set<Long> keywordIds;

    public Keywords(List<Keyword> selectedKeywords) {
        if (selectedKeywords.size() > MAX_KEYWORD_COUNT) {
            throw new KeywordLimitExceedException(MAX_KEYWORD_COUNT);
        }
        if (hasDuplicateKeywords(selectedKeywords)) {
            throw new DuplicateKeywordException();
        }
        this.keywordIds = selectedKeywords.stream()
                .map(Keyword::getId)
                .collect(Collectors.toSet());
    }

    private boolean hasDuplicateKeywords(List<Keyword> selectedKeywords) {
        long distinctKeywordCount = selectedKeywords.stream()
                .distinct()
                .count();
        return selectedKeywords.size() != distinctKeywordCount;
    }

    public Set<Long> getKeywordIds() {
        return Collections.unmodifiableSet(keywordIds);
    }
}
