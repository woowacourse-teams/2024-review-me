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
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.ReviewGroupService;

@RestController
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;
    private final ReviewGroupService reviewGroupService;

    @PostMapping("/v2/groups/{reviewRequestCode}/highlights")
    public ResponseEntity<Void> highlightByReviewGroup(
            @PathVariable String reviewRequestCode,
            @Valid @RequestBody HighlightsRequest request
    ) {
        /*
        TODO : aop 인증 로직 필요 (존재하는 세션에 대해 reviewGroupId와 일치 여부 확인)
        */

        // TODO : reviewRequestCode를 위한 임시 사용, 이후 삭제 예정.
        ReviewGroup reviewGroup = reviewGroupService.getReviewGroupByReviewRequestCode(reviewRequestCode);

        highlightService.highlightByReviewGroup(reviewGroup.getId(), request);
        return ResponseEntity.ok().build();
    }
}
