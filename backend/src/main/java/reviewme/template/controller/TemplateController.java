package reviewme.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.template.dto.response.TemplateResponse;
import reviewme.template.service.TemplateService;

@RestController
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/reviews/write")
    public ResponseEntity<TemplateResponse> findReviewTemplate(@RequestParam String reviewRequestCode) {
        TemplateResponse response = templateService.findDefaultTemplate();
        return ResponseEntity.ok(response);
    }
}
