package reviewme.highlight.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<Void> highlight(
            @Valid @RequestBody HighlightsRequest request,
            @LoginMemberSession(required = false) LoginMember loginMember,
            @GuestReviewGroupSession(required = false) GuestReviewGroup guestReviewGroup
    ) {
        /*
        TODO : aop 인증 로직 필요 (존재하는 세션에 대해 reviewGroupId와 일치 여부 확인)
        */
        highlightService.editHighlight(request);
        return ResponseEntity.ok().build();
    }
}
