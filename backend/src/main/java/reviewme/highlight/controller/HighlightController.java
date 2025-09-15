package reviewme.highlight.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reviewme.highlight.service.HighlightService;
import reviewme.highlight.service.dto.HighlightsRequest;

@RestController
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;

    @PostMapping("/v2/groups/{reviewGroupId}/highlights")
    public ResponseEntity<Void> highlightByReviewGroup(
            @PathVariable long reviewGroupId,
            @Valid @RequestBody HighlightsRequest request
    ) {
        highlightService.highlightByReviewGroup(reviewGroupId, request);
        return ResponseEntity.ok().build();
    }
}
