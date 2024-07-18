package reviewme.keyword.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import reviewme.keyword.dto.response.KeywordsResponse;

@Tag(name = "키워드 관리")
public interface KeywordApi {

    @Operation(
            summary = "모든 키워드 조회",
            description = "모든 키워드를 조회한다."
    )
    ResponseEntity<KeywordsResponse> findAllKeywords();
}
