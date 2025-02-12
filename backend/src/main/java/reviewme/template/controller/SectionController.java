package reviewme.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewme.template.service.TemplateService;
import reviewme.template.service.dto.response.SectionNamesResponse;

@RestController
@RequiredArgsConstructor
public class SectionController {

    private final TemplateService templateService;

    @GetMapping("/v2/sections")
    public ResponseEntity<SectionNamesResponse> getSectionNames() {
        SectionNamesResponse sectionNames = templateService.getSectionNames();
        return ResponseEntity.ok(sectionNames);
    }
}
