package reviewme.reviewgroup.controller;

import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.security.resolver.LoginMemberSession;
import reviewme.security.resolver.dto.LoginMember;
import reviewme.reviewgroup.service.ReviewGroupLookupService;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupPageResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupSummaryResponse;

@RestController
@RequiredArgsConstructor
public class ReviewGroupController {

    private final ReviewGroupService reviewGroupService;
    private final ReviewGroupLookupService reviewGroupLookupService;

    @GetMapping("/v2/groups/summary")
    public ResponseEntity<ReviewGroupSummaryResponse> getReviewGroupSummary(
            @RequestParam String reviewRequestCode
    ) {
        ReviewGroupSummaryResponse response = reviewGroupLookupService.getReviewGroupSummary(reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v2/groups")
    public ResponseEntity<ReviewGroupCreationResponse> createReviewGroup(
            @Valid @RequestBody ReviewGroupCreationRequest request,
            @LoginMemberSession(required = false) LoginMember loginMember
    ) {
        Long memberId = Optional.ofNullable(loginMember).map(LoginMember::id).orElse(null);
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request, memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups")
    public ResponseEntity<ReviewGroupPageResponse> getMyReviewGroups(
            @LoginMemberSession LoginMember loginMember
    ) {
        ReviewGroupPageResponse response = reviewGroupLookupService.getMyReviewGroups();
        return ResponseEntity.ok(response);
    }
}
