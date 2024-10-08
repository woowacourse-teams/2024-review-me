package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.review.service.dto.response.gathered.ReviewsGatherBySectionResponse;

@Service
@RequiredArgsConstructor
public class GatheredReviewLookupService {

    public ReviewsGatherBySectionResponse getReceivedReviewsBySectionId(String reviewRequestCode, long sectionId) {
        return null;
    }
}
