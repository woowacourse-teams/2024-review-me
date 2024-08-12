package reviewme.template.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import reviewme.template.dto.response.TemplateResponse;

@Tag(name = "리뷰 폼 관리")
public interface TemplateApi {

    @Operation(summary = "리뷰 폼 요청", description = "리뷰 작성을 위한 리뷰 폼을 요청한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "응답 성공 : 리뷰 폼 응답",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TemplateResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "응답 실패 : 올바르지 않은 리뷰 요청 코드입니다.",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "type": "about:blank",
                                      "title": "Bad Request",
                                      "status": 400,
                                      "detail": "올바르지 않은 리뷰 요청 코드입니다.",
                                      "instance": "/reviews/write"
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<TemplateResponse> getReviewForm(
            @Parameter(description = "리뷰 요청 코드", required = true)
            @RequestParam String reviewRequestCode
    );
}
