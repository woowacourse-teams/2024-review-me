package reviewme.reviewgroup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reviewme.reviewgroup.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.dto.ReviewGroupCreationResponse;
import reviewme.reviewgroup.service.ReviewGroupService;

@RestController
@RequiredArgsConstructor
public class ReviewGroupController implements ReviewGroupApi {

    private final ReviewGroupService reviewGroupService;

    @PostMapping("/groups")
    public ResponseEntity<ReviewGroupCreationResponse> createReviewGroup(
            @Valid @RequestBody ReviewGroupCreationRequest request
    ) {
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v2/groups")
    public ResponseEntity<ReviewGroupCreationResponse> createReviewGroupV2(
            @Valid @RequestBody ReviewGroupCreationRequest request
    ) {
        ReviewGroupCreationResponse response = reviewGroupService.createReviewGroup(request);
        return ResponseEntity.ok(response);
    }
}
