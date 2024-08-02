package reviewme.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReviewSetupResponse;

@Tag(name = "리뷰 관리")
public interface ReviewApi {

    @Operation(summary = "리뷰 등록", description = "리뷰 작성 정보를 받아 리뷰를 등록한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 등록 요청 성공")
    })
    ResponseEntity<Void> createReview(@Valid @RequestBody CreateReviewRequest request);

    @Operation(summary = "리뷰 작성 정보 조회", description = "리뷰 생성 시 필요한 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 폼 응답 성공",
                    content = @Content(schema = @Schema(implementation = ReviewSetupResponse.class))),
            @ApiResponse(responseCode = "400", description = "응답 실패 : 올바르지 않은 리뷰 요청 코드",
                    content = @Content)
    })
    ResponseEntity<ReviewSetupResponse> findReviewCreationSetup(@RequestParam String reviewRequestCode);

//    @Operation(
//            summary = "리뷰 조회",
//            description = "단일 리뷰를 조회한다."
//    )
//    ResponseEntity<ReviewDetailResponse> findReview(@PathVariable long id, @RequestParam long memberId);
//
//    @Operation(
//            summary = "내가 받은 리뷰 조회",
//            description = "내가 받은 리뷰를 조회한다. (로그인을 구현하지 않은 지금 시점에서는 memberId로 로그인했다고 가정한다.)"
//    )
//    ResponseEntity<ReceivedReviewsResponse> findMyReceivedReview(@RequestParam long memberId,
//                                                                 @RequestParam(required = false) Long lastReviewId,
//                                                                 @RequestParam(defaultValue = "10") int size);
}
