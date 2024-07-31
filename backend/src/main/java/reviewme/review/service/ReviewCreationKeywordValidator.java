package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.keyword.domain.exception.DuplicateKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;
import reviewme.keyword.domain.exception.KeywordNotFoundException;
import reviewme.keyword.repository.KeywordRepository;

@Component
@RequiredArgsConstructor
public class ReviewCreationKeywordValidator {

    private static final int MIN_KEYWORD_COUNT = 1;
    private static final int MAX_KEYWORD_COUNT = 5;

    private final KeywordRepository keywordRepository;

    void validate(List<Long> selectedKeywordIds) {
        validateKeywordCount(selectedKeywordIds.size());
        validateUniqueKeyword(selectedKeywordIds);
        validateExistsKeyword(selectedKeywordIds);
    }

    private void validateUniqueKeyword(List<Long> selectedKeywordIds) {
        int keywordsCount = selectedKeywordIds.size();
        long distinctCount = selectedKeywordIds.stream()
                .distinct()
                .count();
        if (keywordsCount != distinctCount) {
            throw new DuplicateKeywordException();
        }
    }

    private void validateExistsKeyword(List<Long> selectedKeywordIds) {
        boolean doesKeywordExist = selectedKeywordIds.stream()
                .anyMatch(keywordRepository::existsById);
        if (!doesKeywordExist) {
            throw new KeywordNotFoundException();
        }
    }

    private void validateKeywordCount(int keywordsCount) {
        if (keywordsCount < MIN_KEYWORD_COUNT || keywordsCount > MAX_KEYWORD_COUNT) {
            throw new KeywordLimitExceedException(MIN_KEYWORD_COUNT, MAX_KEYWORD_COUNT);
        }
    }
}
