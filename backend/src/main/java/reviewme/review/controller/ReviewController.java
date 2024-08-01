package reviewme.review.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.dto.response.ReviewDetailResponse;
import reviewme.review.dto.response.ReviewSetupResponse;
import reviewme.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {

    private static final String GROUP_ACCESS_CODE_HEADER = "GroupAccessCode";

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Void> createReview(@Valid @RequestBody CreateReviewRequest request) {
        long savedReviewId = reviewService.createReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }

    @GetMapping("/reviews")
    public ResponseEntity<ReceivedReviewsResponse> findReceivedReviews(HttpServletRequest request) {
        String groupAccessCode = request.getHeader(GROUP_ACCESS_CODE_HEADER);
        ReceivedReviewsResponse response = reviewService.findReceivedReviews(groupAccessCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/write")
    public ResponseEntity<ReviewSetupResponse> findReviewCreationSetup(@RequestParam String reviewRequestCode) {
        ReviewSetupResponse response = reviewService.findReviewCreationSetup(reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewDetailResponse> findReceivedReviewDetail(@PathVariable long id, HttpServletRequest request) {
        String groupAccessCode = request.getHeader(GROUP_ACCESS_CODE_HEADER);
        ReviewDetailResponse response = reviewService.findReceivedReviewDetail(groupAccessCode, id);
        return ResponseEntity.ok(response);
    }
}
