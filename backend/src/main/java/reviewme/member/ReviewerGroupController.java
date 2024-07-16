package reviewme.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewerGroupController {

    private final ReviewerGroupService reviewerGroupService;

    @GetMapping("/reviewer-groups/{reviewerGroupId}")
    public ResponseEntity<ReviewerGroupResponse> findReviewerGroup(@PathVariable long reviewerGroupId) {
        ReviewerGroupResponse response = reviewerGroupService.findReviewerGroup(reviewerGroupId);
        return ResponseEntity.ok(response);
    }
}
