package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.domain.SelectedKeywords;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.repository.ReviewKeywordRepository;

@Service
@RequiredArgsConstructor
public class ReviewKeywordService {

    private final ReviewKeywordRepository reviewKeywordRepository;

    @Transactional
    public List<ReviewKeyword> attachSelectedKeywordsOnReview(Review review, List<Keyword> selectedKeywords) {
        reviewKeywordRepository.removeAllByReview(review);
        SelectedKeywords keywords = new SelectedKeywords(selectedKeywords);
        List<ReviewKeyword> reviewKeywords = keywords.getKeywords()
                .stream()
                .map(keyword -> new ReviewKeyword(review, keyword))
                .toList();
        return reviewKeywordRepository.saveAll(reviewKeywords);
    }
}
