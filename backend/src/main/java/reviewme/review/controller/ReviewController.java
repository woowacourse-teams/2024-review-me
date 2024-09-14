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
import reviewme.review.service.ReviewDetailLookupService;
import reviewme.review.service.ReviewListLookupService;
import reviewme.review.service.ReviewRegisterService;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private static final String GROUP_ACCESS_CODE_HEADER = "GroupAccessCode";

    private final ReviewRegisterService reviewRegisterService;
    private final ReviewListLookupService reviewListLookupService;
    private final ReviewDetailLookupService reviewDetailLookupService;

    @PostMapping("/v2/reviews")
    public ResponseEntity<Void> createReview(@Valid @RequestBody ReviewRegisterRequest request) {
        long savedReviewId = reviewRegisterService.registerReview(request);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }

    /**
     * @deprecated since 1.0.2
     * 기존 헤더에 GroupAccessCode를 사용하지 않고, JWT를 활용하여 ReviewRequestCode를 전달합니다.
     */
    @Deprecated(since = "1.0.2", forRemoval = true)
    @GetMapping("/v2/reviews")
    public ResponseEntity<ReceivedReviewsResponse> findReceivedReviews(
            @RequestParam String reviewRequestCode,
            @HeaderProperty(GROUP_ACCESS_CODE_HEADER) String groupAccessCode
    ) {
        ReceivedReviewsResponse response = reviewListLookupService.getReceivedReviews(reviewRequestCode, groupAccessCode);
        return ResponseEntity.ok(response);
    }

    /**
     * @deprecated since 1.0.2
     * 기존 헤더에 GroupAccessCode를 사용하지 않고, JWT를 활용하여 ReviewRequestCode를 전달합니다.
     */
    @Deprecated(since = "1.0.2", forRemoval = true)
    @GetMapping("/v2/reviews/{id}")
    public ResponseEntity<ReviewDetailResponse> findReceivedReviewDetail(
            @PathVariable long id,
            @RequestParam String reviewRequestCode,
            @HeaderProperty(GROUP_ACCESS_CODE_HEADER) String groupAccessCode
    ) {
        ReviewDetailResponse response = reviewDetailLookupService.getReviewDetail(
                id, reviewRequestCode, groupAccessCode
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vx/reviews")
    public ResponseEntity<ReceivedReviewsResponse> findReceivedReviews2(
            @ReviewRequestCode String reviewRequestCode
    ) {
        ReceivedReviewsResponse response = reviewListLookupService.getReceivedReviews2(reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vx/reviews/{id}")
    public ResponseEntity<ReviewDetailResponse> findReceivedReviewDetail2(
            @PathVariable long id,
            @ReviewRequestCode String reviewRequestCode
    ) {
        ReviewDetailResponse response = reviewDetailLookupService.getReviewDetail2(id, reviewRequestCode);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/vx/token")
//    public ResponseEntity<ReviewRequestTokenResponse> createReviewRequestToken(
//            @Valid @RequestBody ReviewRequestTokenRequest request
//    ) {
//        ReviewRequestTokenResponse response = reviewRequestTokenService.createToken(request);
//        return ResponseEntity.ok(response);
//    }
}
