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
import reviewme.auth.controller.GuestReviewGroupSession;
import reviewme.auth.controller.LoginMemberSession;
import reviewme.auth.controller.dto.GuestReviewGroup;
import reviewme.auth.controller.dto.LoginMember;
import reviewme.review.service.ReviewDetailLookupService;
import reviewme.review.service.ReviewGatheredLookupService;
import reviewme.review.service.ReviewListLookupService;
import reviewme.review.service.ReviewRegisterService;
import reviewme.review.service.ReviewSummaryService;
import reviewme.review.service.dto.request.ReviewRegisterRequest;
import reviewme.review.service.dto.response.detail.ReviewDetailResponse;
import reviewme.review.service.dto.response.gathered.ReviewsGatheredBySectionResponse;
import reviewme.review.service.dto.response.list.AuthoredReviewsResponse;
import reviewme.review.service.dto.response.list.ReviewCountResponse;
import reviewme.review.service.dto.response.list.ReviewPageResponse;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRegisterService reviewRegisterService;
    private final ReviewListLookupService reviewListLookupService;
    private final ReviewDetailLookupService reviewDetailLookupService;
    private final ReviewSummaryService reviewSummaryService;
    private final ReviewGatheredLookupService reviewGatheredLookupService;

    @GetMapping("/v2/reviews/{id}")
    public ResponseEntity<ReviewDetailResponse> findReviewDetail(
            @PathVariable long id,
            @LoginMemberSession(required = false) LoginMember loginMember,
            @GuestReviewGroupSession(required = false) GuestReviewGroup guestReviewGroup
    ) {
        /*
        TODO : aop 인증 로직 필요 (존재하는 세션에 대해 reviewId와 일치 여부 확인)
        */
        ReviewDetailResponse response = reviewDetailLookupService.getReviewDetail(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups/{reviewGroupId}/reviews")
    public ResponseEntity<ReviewPageResponse> findReviewsByGroup(
            @PathVariable long reviewGroupId,
            @RequestParam(required = false) Long lastReviewId,
            @RequestParam(required = false) Integer size,
            @LoginMemberSession(required = false) LoginMember loginMember,
            @GuestReviewGroupSession(required = false) GuestReviewGroup guestReviewGroup
    ) {
        /*
        TODO : aop 인증 로직 필요 (존재하는 세션에 대해 reviewGroupId와 일치 여부 확인)
        */
        ReviewPageResponse response = reviewListLookupService.getReviewsByGroup(reviewGroupId, lastReviewId, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups/{reviewGroupId}/reviews/count")
    public ResponseEntity<ReviewCountResponse> findReviewCountByGroup(
            @PathVariable long reviewGroupId,
            @LoginMemberSession(required = false) LoginMember loginMember,
            @GuestReviewGroupSession(required = false) GuestReviewGroup guestReviewGroup
    ) {
        /*
        TODO : aop 인증 로직 필요 (존재하는 세션에 대해 reviewGroupId와 일치 여부 확인)
        */
        ReviewCountResponse response = reviewSummaryService.getReviewCountByGroup(reviewGroupId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/reviews/authored")
    public ResponseEntity<AuthoredReviewsResponse> findAuthoredReviews(
            @RequestParam(required = false) Long lastReviewId,
            @RequestParam(required = false) Integer size,
            @LoginMemberSession LoginMember loginMember
    ) {
        AuthoredReviewsResponse response = reviewListLookupService.getAuthoredReviews(lastReviewId, size, loginMember.id());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v2/reviews")
    public ResponseEntity<Void> createReview(
            @Valid @RequestBody ReviewRegisterRequest request,
            @LoginMemberSession(required = false) LoginMember loginMember
    ) {
        Long memberId = Optional.ofNullable(loginMember).map(LoginMember::id).orElse(null);
        long savedReviewId = reviewRegisterService.registerReview(request, memberId);
        return ResponseEntity.created(URI.create("/reviews/" + savedReviewId)).build();
    }


    /*
        특정 Page 종속적인 API 목록
     */

    @GetMapping("/v2/groups/{reviewGroupId}/reviews/gather")
    public ResponseEntity<ReviewsGatheredBySectionResponse> getReviewsByGroupAndSection(
            @PathVariable long reviewGroupId,
            @RequestParam("sectionId") long sectionId,
            @LoginMemberSession(required = false) LoginMember loginMember,
            @GuestReviewGroupSession(required = false) GuestReviewGroup guestReviewGroup
    ) {
        /*
        TODO : aop 인증 로직 필요 (존재하는 세션에 대해 reviewGroupId와 일치 여부 확인)
        */
        ReviewsGatheredBySectionResponse response =
                reviewGatheredLookupService.getReviewsByGroupAndSection(reviewGroupId, sectionId);
        return ResponseEntity.ok(response);
    }
}

