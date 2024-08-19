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
import reviewme.review.service.CreateReviewService;
import reviewme.review.service.ReviewDetailLookupService;
import reviewme.review.service.ReviewService;
import reviewme.review.service.dto.request.CreateReviewRequest;
import reviewme.review.service.dto.response.detail.TemplateAnswerResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private static final String GROUP_ACCESS_CODE_HEADER = "GroupAccessCode";

    private final CreateReviewService createReviewService;
    private final ReviewService reviewService;
    private final ReviewDetailLookupService reviewDetailLookupService;

    @PostMapping("/v2/reviews")
    public ResponseEntity<Void> createReview(@Valid @RequestBody CreateReviewRequest request) {
        long savedReviewId = createReviewService.createReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }

    @GetMapping("/v2/reviews")
    public ResponseEntity<ReceivedReviewsResponse> findReceivedReviews(
            @RequestParam String reviewRequestCode,
            @HeaderProperty(GROUP_ACCESS_CODE_HEADER) String groupAccessCode
    ) {
        ReceivedReviewsResponse response = reviewService.findReceivedReviews(reviewRequestCode, groupAccessCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews/{id}")
    public ResponseEntity<TemplateAnswerResponse> findReceivedReviewDetail(
            @PathVariable long id,
            @RequestParam String reviewRequestCode,
            @HeaderProperty(GROUP_ACCESS_CODE_HEADER) String groupAccessCode
    ) {
        TemplateAnswerResponse response = reviewDetailLookupService.getReviewDetail(
                id, reviewRequestCode, groupAccessCode
        );
        return ResponseEntity.ok(response);
    }
}
