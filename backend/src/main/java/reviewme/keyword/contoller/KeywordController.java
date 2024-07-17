package reviewme.keyword.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewme.keyword.dto.response.KeywordsResponse;
import reviewme.keyword.service.KeywordService;

@Tag(name = "키워드 관리")
@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @Operation(summary = "모든 키워드 조회", description = "모든 키워드를 조회한다.")
    @GetMapping("/keywords")
    public ResponseEntity<KeywordsResponse> findAllKeywords() {
        KeywordsResponse response = keywordService.findAllKeywords();
        return ResponseEntity.ok(response);
    }
}
