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
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.dto.response.ReviewDetailResponse;
import reviewme.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<Void> createReview(@Valid @RequestBody CreateReviewRequest request) {
        long id = reviewService.createReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + id)).build();
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewDetailResponse> findReview(@PathVariable long id,
                                                           @RequestParam long memberId) {
        ReviewDetailResponse response = reviewService.findReview(id, memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews")
    public ResponseEntity<ReceivedReviewsResponse> findMyReceivedReview(@RequestParam long memberId,
                                                                        @RequestParam(required = false) Long lastReviewId,
                                                                        @RequestParam(defaultValue = "10") int size) {
        ReceivedReviewsResponse myReceivedReview = reviewService.findMyReceivedReview(memberId, lastReviewId, size);
        return ResponseEntity.ok(myReceivedReview);
    }
}
