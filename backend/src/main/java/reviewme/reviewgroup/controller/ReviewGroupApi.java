package reviewme.reviewgroup.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationRequest;
import reviewme.reviewgroup.service.dto.ReviewGroupCreationResponse;

@Tag(name = "리뷰 그룹 관리")
public interface ReviewGroupApi {

    @Operation(summary = "리뷰 그룹 생성", description = "리뷰 그룹 정보를 받아 리뷰 그룹을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "응답 성공 : 리뷰 그룹 생성",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReviewGroupCreationRequest.class)
                    )
            )
    })
    ResponseEntity<ReviewGroupCreationResponse> createReviewGroup(
            @Valid @RequestBody ReviewGroupCreationRequest request
    );
}
