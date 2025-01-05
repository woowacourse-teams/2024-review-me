package reviewme.template.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.dto.response.SectionNameResponse;
import reviewme.template.service.dto.response.SectionNamesResponse;
import reviewme.template.service.dto.response.SectionResponse;
import reviewme.template.service.dto.response.TemplateResponse;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;
import reviewme.template.service.mapper.TemplateMapper;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final ReviewGroupService reviewGroupService;
    private final TemplateMapper templateMapper;
    private final TemplateRepository templateRepository;

    @Transactional(readOnly = true)
    public TemplateResponse generateReviewForm(String reviewRequestCode) {
        ReviewGroup reviewGroup = reviewGroupService.getReviewGroupByReviewRequestCode(reviewRequestCode);
        List<SectionResponse> sectionResponses = templateMapper.mapSectionResponses(
                reviewGroup.getId(), reviewGroup.getTemplateId()
        );
        return new TemplateResponse(
                reviewGroup.getTemplateId(), reviewGroup.getReviewee(), reviewGroup.getProjectName(), sectionResponses
        );
    }

    @Transactional(readOnly = true)
    public SectionNamesResponse getSectionNames(ReviewGroup reviewGroup) {
        Template template = templateRepository.findById(reviewGroup.getTemplateId())
                .orElseThrow(() -> new TemplateNotFoundByReviewGroupException(reviewGroup.getId(),
                        reviewGroup.getTemplateId()));

        List<SectionNameResponse> sectionNameResponses = template.getSections()
                .stream()
                .map(section -> new SectionNameResponse(section.getId(), section.getSectionName()))
                .toList();

        return new SectionNamesResponse(sectionNameResponses);
    }
}
