package reviewme.review.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import reviewme.review.service.dto.request.CreateReviewRequest;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;
import reviewme.review.service.dto.response.detail.TemplateAnswerResponse;

@Tag(name = "리뷰 관리")
public interface ReviewApi {

    /**
     * v2
     */
    @Operation(summary = "리뷰 등록", description = "리뷰 작성 정보를 받아 리뷰를 등록한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "응답 성공 : 리뷰 등록 완료",
                    headers = {
                            @Header(name = "Content-Type", description = APPLICATION_JSON_VALUE),
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "응답 실패 : 올바르지 않은 리뷰 요청 코드",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "about:blank",
                                      "title": "Bad Request",
                                      "status": 400,
                                      "detail": "올바르지 않은 리뷰 요청 코드입니다.",
                                      "instance": "/reviews"
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<Void> createReview(@Valid @RequestBody CreateReviewRequest request);


    @Operation(summary = "내가 받은 리뷰 목록 조회", description = "내가 받은 리뷰들을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "응답 성공 : 리뷰 목록 응답",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ReceivedReviewsResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "응답 실패 : 올바르지 않은 그룹 액세스 코드",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "about:blank",
                                      "title": "Bad Request",
                                      "status": 400,
                                      "detail": "올바르지 않은 그룹 확인 코드입니다.",
                                      "instance": "/reviews"
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ReceivedReviewsResponse> findReceivedReviews2(
            @Parameter(
                    description = "리뷰 그룹 액세스 코드",
                    required = true
            ) @RequestHeader("GroupAccessCode") String groupAccessCode
    );

    @Operation(summary = "리뷰 상세 조회", description = "하나의 리뷰를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "응답 성공 : 리뷰 정보 응답",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TemplateAnswerResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "응답 실패 : 올바르지 않은 그룹 액세스 코드",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "about:blank",
                                      "title": "Bad Request",
                                      "status": 400,
                                      "detail": "올바르지 않은 그룹 확인 코드입니다.",
                                      "instance": "/reviews/{id}"
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<TemplateAnswerResponse> findReceivedReviewDetailV2(
            @Parameter(
                    description = "조회할 리뷰 ID",
                    required = true,
                    example = "1"
            ) @PathVariable long id,

            @Parameter(
                    description = "리뷰 그룹 액세스 코드",
                    required = true
            ) @RequestHeader("GroupAccessCode") String groupAccessCode);
}
