package reviewme.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reviewme.member.dto.response.ReviewerGroupResponse;
import reviewme.member.service.ReviewerGroupService;

@Tag(name = "리뷰어 그룹 관리")
@RestController
@RequiredArgsConstructor
public class ReviewerGroupController {

    private final ReviewerGroupService reviewerGroupService;

    @Operation(summary = "리뷰어 그룹 조회", description = "리뷰어 그룹을 조회한다.",
    responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = ReviewerGroupResponse.class)))})
    @GetMapping("/reviewer-groups/{id}")
    public ResponseEntity<ReviewerGroupResponse> findReviewerGroup(@PathVariable long id) {
        ReviewerGroupResponse response = reviewerGroupService.findReviewerGroup(id);
        return ResponseEntity.ok(response);
    }
}
