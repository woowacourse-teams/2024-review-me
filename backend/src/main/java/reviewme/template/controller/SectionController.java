package reviewme.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import reviewme.template.service.SectionService;
import reviewme.template.service.dto.response.SectionNamesResponse;

@RestController
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("/v2/sections")
    public ResponseEntity<SectionNamesResponse> getSectionNames(
            @SessionAttribute("reviewRequestCode") String reviewRequestCode
    ) {
        SectionNamesResponse sectionNames = sectionService.getSectionNames(reviewRequestCode);
        return ResponseEntity.ok(sectionNames);
    }
}
