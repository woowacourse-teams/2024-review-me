package reviewme.review.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.security.resolver.LoginMemberSession;
import reviewme.security.resolver.dto.LoginMember;
import reviewme.security.aspect.RequireReviewAccess;
import reviewme.security.aspect.RequireReviewGroupAccess;
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
            @Valid @RequestBody ReviewRegisterRequest request,
            @LoginMemberSession(required = false) LoginMember loginMember
    ) {
        Long memberId = Optional.ofNullable(loginMember).map(LoginMember::id).orElse(null);
        long savedReviewId = reviewRegisterService.registerReview(request, memberId);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }

    @GetMapping("/v2/groups/{reviewGroupId}/reviews/received")
    @RequireReviewGroupAccess(target = "#reviewGroupId")
    public ResponseEntity<ReceivedReviewPageResponse> findReceivedReviews(
            @PathVariable long reviewGroupId,
            @RequestParam(required = false) Long lastReviewId,
            @RequestParam(required = false) Integer size
    ) {
        ReceivedReviewPageResponse response = reviewListLookupService.getReceivedReviews(reviewGroupId, lastReviewId, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews/{id}")
    @RequireReviewAccess(target = "#id")
    public ResponseEntity<ReviewDetailResponse> findReceivedReviewDetail(
            @PathVariable long id
    ) {
        ReviewDetailResponse response = reviewDetailLookupService.getReviewDetail(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups/{reviewGroupId}/reviews/summary")
    @RequireReviewGroupAccess(target = "#reviewGroupId")
    public ResponseEntity<ReceivedReviewsSummaryResponse> findReceivedReviewOverview(
            @PathVariable long reviewGroupId
    ) {
        ReceivedReviewsSummaryResponse response = reviewSummaryService.getReviewSummary(reviewGroupId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups/{reviewGroupId}/reviews/gather")
    @RequireReviewGroupAccess(target = "#reviewGroupId")
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
            @RequestParam(required = false) Integer size,
            @LoginMemberSession LoginMember loginMember
    ) {
        AuthoredReviewsResponse response = reviewListLookupService.getAuthoredReviews(lastReviewId, size,
                loginMember.id());
        return ResponseEntity.ok(response);
    }
}
