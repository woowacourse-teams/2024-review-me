package reviewme.highlight.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import reviewme.highlight.service.HighlightService;
import reviewme.highlight.service.dto.HighlightsRequest;

@RestController
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;

    @PostMapping("/v2/highlight")
    public ResponseEntity<Void> highlight(@Valid @RequestBody HighlightsRequest request,
                                          @SessionAttribute("reviewRequestCode") String reviewRequestCode) {
        highlightService.highlight(request, reviewRequestCode);
        return ResponseEntity.ok().build();
    }
}
