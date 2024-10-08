package reviewme.review.controller;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import reviewme.review.service.GatheredReviewLookupService;
import reviewme.review.service.ReviewDetailLookupService;
import reviewme.review.service.ReviewListLookupService;
import reviewme.review.service.ReviewRegisterService;
import reviewme.review.service.ReviewSummaryService;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.gathered.ReviewsGatherBySectionResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private static final String GROUP_ACCESS_CODE_HEADER = "GroupAccessCode";

    private final ReviewRegisterService reviewRegisterService;
    private final ReviewListLookupService reviewListLookupService;
    private final ReviewDetailLookupService reviewDetailLookupService;
    private final ReviewSummaryService reviewSummaryService;
    private final GatheredReviewLookupService gatheredReviewLookupService;

    @PostMapping("/v2/reviews")
    public ResponseEntity<Void> createReview(@Valid @RequestBody ReviewRegisterRequest request) {
        long savedReviewId = reviewRegisterService.registerReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }

    @GetMapping("/v2/reviews")
    public ResponseEntity<ReceivedReviewsResponse> findReceivedReviews(
            @RequestParam(required = false) Long lastReviewId,
            @RequestParam(required = false) Integer size,
            @SessionAttribute("reviewRequestCode") String reviewRequestCode
    ) {
        ReceivedReviewsResponse response = reviewListLookupService.getReceivedReviews(
                lastReviewId, size, reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews/{id}")
    public ResponseEntity<ReviewDetailResponse> findReceivedReviewDetail(
            @PathVariable long id,
            @SessionAttribute("reviewRequestCode") String reviewRequestCode
    ) {
        ReviewDetailResponse response = reviewDetailLookupService.getReviewDetail(id, reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews/summary")
    public ResponseEntity<ReceivedReviewsSummaryResponse> findReceivedReviewOverview(
            @SessionAttribute("reviewRequestCode") String reviewRequestCode
    ) {
        ReceivedReviewsSummaryResponse response = reviewSummaryService.getReviewSummary(reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews/gather")
    public ResponseEntity<ReviewsGatherBySectionResponse> getReceivedReviewsBySectionId(
            @RequestParam("sectionId") Long sectionId,
            @SessionAttribute("reviewRequestCode") String reviewRequestCode
    ) {
        ReviewsGatherBySectionResponse response = gatheredReviewLookupService.getReceivedReviewsBySectionId(
                reviewRequestCode, sectionId);
        return ResponseEntity.ok(response);
    }
}
