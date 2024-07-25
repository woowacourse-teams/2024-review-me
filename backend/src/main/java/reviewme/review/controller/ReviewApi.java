package reviewme.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReviewDetailResponse;

@Tag(name = "리뷰 관리")
public interface ReviewApi {

    @Operation(
            summary = "리뷰 등록",
            description = "리뷰 작성 정보를 받아 리뷰를 등록한다.",
            responses = @ApiResponse(responseCode = "201")
    )
    ResponseEntity<Void> createReview(@RequestBody CreateReviewRequest request);

    @Operation(
            summary = "리뷰 조회",
            description = "단일 리뷰를 조회한다."
    )
    ResponseEntity<ReviewDetailResponse> findReview(@PathVariable long id);
}
