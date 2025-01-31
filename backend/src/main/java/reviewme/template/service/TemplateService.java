package reviewme.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.dto.response.SectionNamesResponse;
import reviewme.template.service.dto.response.TemplateResponse;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final ReviewGroupService reviewGroupService;
    private final TemplateRepository templateRepository;

    @Transactional(readOnly = true)
    public TemplateResponse generateReviewForm(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupService.getReviewGroupByReviewRequestCode(reviewRequestCode);
        Template template = templateRepository.findById(reviewGroup.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(
                        reviewGroup.getId(), reviewGroup.getTemplateId())
                );
        return TemplateResponse.of(reviewGroup, template);
    }

    @Transactional(readOnly = true)
    public SectionNamesResponse getSectionNames(ReviewGroup reviewGroup) {
        Template template = templateRepository.findById(reviewGroup.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(
                        reviewGroup.getId(), reviewGroup.getTemplateId())
                );
        return SectionNamesResponse.from(template);
    }
}
