package reviewme.reviewgroup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.global.HeaderProperty;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.reviewgroup.service.dto.CheckGroupAccessCodeResponse;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;

@RestController
@RequiredArgsConstructor
public class ReviewGroupController implements ReviewGroupApi {

    private final ReviewGroupService reviewGroupService;

    @PostMapping("/v2/groups")
    public ResponseEntity<ReviewGroupCreationResponse> createReviewGroup(
            @Valid @RequestBody ReviewGroupCreationRequest request
    ) {
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2/groups/code")
    public ResponseEntity<CheckGroupAccessCodeResponse> checkGroupAccessCode(
            @RequestParam("reviewRequestCode") String reviewRequestCode,
            @HeaderProperty("GroupAccessCode") String groupAccessCode
    ) {
        CheckGroupAccessCodeResponse response
                = reviewGroupService.checkGroupAccessCode(reviewRequestCode, groupAccessCode);
        return ResponseEntity.ok(response);
    }
}
