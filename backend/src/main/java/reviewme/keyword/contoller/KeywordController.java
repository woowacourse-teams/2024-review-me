package reviewme.keyword.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewme.keyword.dto.response.KeywordsResponse;
import reviewme.keyword.service.KeywordService;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/keywords")
    public ResponseEntity<KeywordsResponse> findAllKeywords() {
        KeywordsResponse response = keywordService.findAllKeywords();
        return ResponseEntity.ok(response);
    }
}
