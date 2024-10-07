package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.review.service.dto.response.gathered.GatheredReviewsResponse;

@Service
@RequiredArgsConstructor
public class GatheredReviewLookupService {

    public GatheredReviewsResponse getReceivedReviewsBySectionId(String reviewRequestCode, long sectionId) {
        return null;
    }
}
