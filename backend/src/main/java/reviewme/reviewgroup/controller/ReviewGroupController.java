package reviewme.reviewgroup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupResponse;
import reviewme.reviewgroup.service.ReviewGroupFindService;

@RestController
@RequiredArgsConstructor
public class ReviewGroupController implements ReviewGroupApi {

    private final ReviewGroupService reviewGroupService;
    private final ReviewGroupFindService findService;

    @PostMapping("/v2/groups")
    public ResponseEntity<ReviewGroupCreationResponse> createReviewGroup(
            @Valid @RequestBody ReviewGroupCreationRequest request
    ) {
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups")
    public ResponseEntity<ReviewGroupResponse> findReviewGroup(@RequestParam String reviewRequestCode) {
        ReviewGroupResponse response = findService.findReviewGroup(reviewRequestCode);
        return ResponseEntity.ok(response);
    }
}
