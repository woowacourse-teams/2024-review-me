package reviewme.reviewgroup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.reviewgroup.service.ReviewGroupLookupService;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupPageResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupResponse;

@RestController
@RequiredArgsConstructor
public class ReviewGroupController {

    private final ReviewGroupService reviewGroupService;
    private final ReviewGroupLookupService reviewGroupLookupService;

    @GetMapping("/v2/groups/summary")
    public ResponseEntity<ReviewGroupResponse> getReviewGroupSummary(@RequestParam String reviewRequestCode) {
        ReviewGroupResponse response = reviewGroupLookupService.getReviewGroupSummary(reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v2/groups")
    public ResponseEntity<ReviewGroupCreationResponse> createReviewGroup(
            @Valid @RequestBody ReviewGroupCreationRequest request
            /*
            TODO: 회원 세션 임시 사용 방식, 이후 리졸버를 통해 객체로 받아와야 함
            @Nullable @LoginMember Member member
             */
    ) {
        /*
        TODO: 회원 세션 유무에 따른 분기처리 로직
        Long memberId = Optional.ofNullable(member).map(Member::getId).orElse(null);
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request, memberId);
        return ResponseEntity.ok(response);
        */
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request, null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups")
    public ResponseEntity<ReviewGroupPageResponse> getMyReviewGroups() {
        // TODO: 세션을 활용한 권한 체계에 따른 추가 조치 필요
        ReviewGroupPageResponse response = reviewGroupLookupService.getMyReviewGroups();
        return ResponseEntity.ok(response);
    }
}
