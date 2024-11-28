package reviewme.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.template.domain.structured.StructuredTemplate;
import reviewme.template.domain.structured.StructuredTemplateFinder;

@Service
@RequiredArgsConstructor
public class StructuredTemplateService {

    private final StructuredTemplateFinder structuredTemplateFinder;

    @Transactional
    public StructuredTemplate getStructuredTemplateById(long templateId) {
        return structuredTemplateFinder.find(templateId);
    }
}
