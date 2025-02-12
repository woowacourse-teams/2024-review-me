package reviewme.highlight.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reviewme.global.authorization.RequireReviewGroupAccess;
import reviewme.security.resolver.GuestReviewGroupSession;
import reviewme.security.resolver.LoginMemberSession;
import reviewme.security.resolver.dto.GuestReviewGroup;
import reviewme.security.resolver.dto.LoginMember;
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
