package reviewme.highlight.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reviewme.security.aspect.RequireReviewGroupAccess;
import reviewme.highlight.service.HighlightService;
import reviewme.highlight.service.dto.HighlightsRequest;

@RestController
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;

    @PostMapping("/v2/highlight")
    @RequireReviewGroupAccess(target = "#request.reviewGroupId()")
    public ResponseEntity<Void> highlight(
            @Valid @RequestBody HighlightsRequest request
    ) {
        highlightService.editHighlight(request);
        return ResponseEntity.ok().build();
    }
}
