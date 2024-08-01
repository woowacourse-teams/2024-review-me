package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(name = "리뷰 상세 조회 응답")
public record ReviewDetailResponse(

        @Schema(description = "리뷰 ID")
        long id,

        @Schema(description = "리뷰 작성일")
        LocalDate createdAt,

        @Schema(description = "프로젝트명")
        String projectName,

        @Schema(description = "리뷰이 이름")
        String revieweeName,

        @Schema(description = "리뷰 문항 목록")
        List<ReviewContentResponse> contents,

        @Schema(description = "키워드 목록")
        List<KeywordResponse> keywords
) {
}
