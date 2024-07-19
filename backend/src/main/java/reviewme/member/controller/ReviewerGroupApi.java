package reviewme.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import reviewme.member.dto.response.ReviewerGroupResponse;

@Tag(name = "리뷰어 그룹 관리")
public interface ReviewerGroupApi {

    @Operation(
            summary = "리뷰어 그룹 조회",
            description = "리뷰어 그룹을 조회한다."
    )
    ResponseEntity<ReviewerGroupResponse> findReviewerGroup(@PathVariable long id);
}
