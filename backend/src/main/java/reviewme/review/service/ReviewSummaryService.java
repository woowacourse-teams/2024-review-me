package reviewme.review.service;

import org.springframework.stereotype.Service;
import reviewme.review.service.dto.response.list.ReceivedReviewSummaryResponse;

@Service
public class ReviewSummaryService {

    public ReceivedReviewSummaryResponse getReviewSummary(String reviewRequestCode) {
        // todo: reviewRequestCode 에 해당하는 리뷰를 찾고, 요약 정보를 반환한다.
        return null;
    }
}
