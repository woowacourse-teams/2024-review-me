package reviewme.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.DefaultTemplateNotFoundException;

@Service
@RequiredArgsConstructor
public class DefaultTemplateService {

    private static final long DEFAULT_TEMPLATE_Id = 1L;

    private final TemplateRepository templateRepository;

    @Transactional(readOnly = true)
    public Template getDefaultTemplate() {
        return templateRepository.findById(DEFAULT_TEMPLATE_Id)
                .orElseThrow(() -> new DefaultTemplateNotFoundException(DEFAULT_TEMPLATE_Id));
    }
}
