package reviewme.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.exception.ReviewGroupNotFoundByRequestReviewCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Template;
import reviewme.template.domain.exception.DefaultTemplateNotFoundException;
import reviewme.template.service.dto.response.TemplateResponse;
import reviewme.template.repository.TemplateRepository;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private static final long USE_TEMPLATE_ID = 1L;

    private final ReviewGroupRepository reviewGroupRepository;
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;

    @Transactional(readOnly = true)
    public TemplateResponse generateReviewForm(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByRequestReviewCodeException(reviewRequestCode));

        Template defaultTemplate = templateRepository.findById(USE_TEMPLATE_ID)
                .orElseThrow(() -> new DefaultTemplateNotFoundException(USE_TEMPLATE_ID));

        return templateMapper.mapToTemplateResponse(reviewGroup, defaultTemplate);
    }
}
