package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredBySectionResponse;

@Service
@RequiredArgsConstructor
public class GatheredReviewLookupService {

    public ReviewsGatheredBySectionResponse getReceivedReviewsBySectionId(String reviewRequestCode, long sectionId) {
        return null;
    }
}
