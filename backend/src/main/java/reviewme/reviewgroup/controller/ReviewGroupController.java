package reviewme.reviewgroup.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupResponse;

@RestController
@RequiredArgsConstructor
public class ReviewGroupController {

    private final ReviewGroupService reviewGroupService;
    private final ReviewGroupLookupService reviewGroupLookupService;

    @GetMapping("/v2/groups")
    public ResponseEntity<ReviewGroupResponse> getReviewGroupSummary(@RequestParam String reviewRequestCode) {
        ReviewGroupResponse response = reviewGroupLookupService.getReviewGroupSummary(reviewRequestCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v2/groups")
    public ResponseEntity<ReviewGroupCreationResponse> createReviewGroup(
            @Valid @RequestBody ReviewGroupCreationRequest request
    ) {
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v2/groups/check")
    public ResponseEntity<Void> checkGroupAccessCode(
            @Valid @RequestBody CheckValidAccessRequest request,
            HttpServletRequest httpRequest
    ) {
        reviewGroupService.checkGroupAccessCode(request);
        HttpSession session = httpRequest.getSession();
        session.setAttribute("reviewRequestCode", request.reviewRequestCode());
        return ResponseEntity.noContent().build();
    }
}
