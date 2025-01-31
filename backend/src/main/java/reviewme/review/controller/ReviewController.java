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
import reviewme.review.service.ReviewDetailLookupService;
import reviewme.review.service.ReviewGatheredLookupService;
import reviewme.review.service.ReviewListLookupService;
import reviewme.review.service.ReviewRegisterService;
import reviewme.review.service.ReviewSummaryService;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredBySectionResponse;
import reviewme.review.service.dto.response.list.AuthoredReviewsResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewPageResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsSummaryResponse;
import reviewme.reviewgroup.controller.ReviewGroupSession;
import reviewme.reviewgroup.domain.ReviewGroup;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRegisterService reviewRegisterService;
    private final ReviewListLookupService reviewListLookupService;
    private final ReviewDetailLookupService reviewDetailLookupService;
    private final ReviewSummaryService reviewSummaryService;
    private final ReviewGatheredLookupService reviewGatheredLookupService;

    @PostMapping("/v2/reviews")
    public ResponseEntity<Void> createReview(
            @Valid @RequestBody ReviewRegisterRequest request
            /*
            TODO: 회원 세션 임시 사용 방식, 이후 리졸버를 통해 객체로 받아와야 함
            @Nullable @LoginMember Member member
             */
    ) {
        /*
        TODO: 회원 세션 유무에 따른 분기처리 로직
        Long memberId = Optional.ofNullable(member).map(Member::getId).orElse(null);
         */
        long savedReviewId = reviewRegisterService.registerReview(request, null);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }

    @GetMapping("/v2/groups/{reviewGroupId}/reviews/received")
    public ResponseEntity<ReceivedReviewPageResponse> findReceivedReviews(
            @PathVariable long reviewGroupId,
            @RequestParam(required = false) Long lastReviewId,
            @RequestParam(required = false) Integer size
    ) {
        ReceivedReviewPageResponse response = reviewListLookupService.getReceivedReviews(reviewGroupId, lastReviewId, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews/{id}")
    public ResponseEntity<ReviewDetailResponse> findReceivedReviewDetail(
            @PathVariable long id,
            @ReviewGroupSession ReviewGroup reviewGroup
    ) {
        ReviewDetailResponse response = reviewDetailLookupService.getReviewDetail(id, reviewGroup);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups/{reviewGroupId}/reviews/summary")
    public ResponseEntity<ReceivedReviewsSummaryResponse> findReceivedReviewOverview(
            @PathVariable long reviewGroupId
    ) {
        ReceivedReviewsSummaryResponse response = reviewSummaryService.getReviewSummary(reviewGroupId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups/{reviewGroupId}/reviews/gather")
    public ResponseEntity<ReviewsGatheredBySectionResponse> getReceivedReviewsBySectionId(
            @PathVariable long reviewGroupId,
            @RequestParam("sectionId") long sectionId
    ) {
        ReviewsGatheredBySectionResponse response =
                reviewGatheredLookupService.getReceivedReviewsBySectionId(reviewGroupId, sectionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews/authored")
    public ResponseEntity<AuthoredReviewsResponse> findAuthoredReviews(
            @RequestParam(required = false) Long lastReviewId,
            @RequestParam(required = false) Integer size
//            @MemberSession Member member
            // TODO: 세션을 활용한 권한 체계에 따른 추가 조치 필요
    ) {
        AuthoredReviewsResponse response = reviewListLookupService.getAuthoredReviews(lastReviewId, size);
        return ResponseEntity.ok(response);
    }
}
