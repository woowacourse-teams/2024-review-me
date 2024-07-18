package reviewme.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reviewme.member.dto.response.ReviewerGroupResponse;
import reviewme.member.service.ReviewerGroupService;

@RestController
@RequiredArgsConstructor
public class ReviewerGroupController implements ReviewerGroupApi {

    private final ReviewerGroupService reviewerGroupService;

    @GetMapping("/reviewer-groups/{id}")
    public ResponseEntity<ReviewerGroupResponse> findReviewerGroup(@PathVariable long id) {
        ReviewerGroupResponse response = reviewerGroupService.findReviewerGroup(id);
        return ResponseEntity.ok(response);
    }
}
