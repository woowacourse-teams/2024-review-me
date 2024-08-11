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
import reviewme.global.HeaderProperty;
import reviewme.review.dto.request.create.CreateReviewRequest;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.dto.response.ReceivedReviewsResponse2;
import reviewme.review.dto.response.ReviewDetailResponse;
import reviewme.review.dto.response.ReviewSetupResponse;
import reviewme.review.service.CreateReviewService;
import reviewme.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private static final String GROUP_ACCESS_CODE_HEADER = "GroupAccessCode";

    private final CreateReviewService createReviewService;
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Void> createReview(
            @Valid @RequestBody reviewme.review.dto.request.CreateReviewRequest request) {
        long savedReviewId = reviewService.createReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }

    @PostMapping("/v2/reviews")
    public ResponseEntity<Void> createReview(@Valid @RequestBody CreateReviewRequest request) {
        long savedReviewId = createReviewService.createReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }

    @GetMapping("/reviews/write")
    public ResponseEntity<ReviewSetupResponse> findReviewCreationSetup(@RequestParam String reviewRequestCode) {
        ReviewSetupResponse response = reviewService.findReviewCreationSetup(reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews")
    public ResponseEntity<ReceivedReviewsResponse> findReceivedReviews(
            @HeaderProperty(GROUP_ACCESS_CODE_HEADER) String groupAccessCode
    ) {
        ReceivedReviewsResponse response = reviewService.findReceivedReviews(groupAccessCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews")
    public ResponseEntity<ReceivedReviewsResponse2> findReceivedReviews2(
            @HeaderProperty(GROUP_ACCESS_CODE_HEADER) String groupAccessCode
    ) {
        ReceivedReviewsResponse2 response = reviewService.findReceivedReviews2(groupAccessCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewDetailResponse> findReceivedReviewDetail(
            @PathVariable long id,
            @HeaderProperty(GROUP_ACCESS_CODE_HEADER) String groupAccessCode) {
        ReviewDetailResponse response = reviewService.findReceivedReviewDetail(groupAccessCode, id);
        return ResponseEntity.ok(response);
    }
}
