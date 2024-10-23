package reviewme.template.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.repository.SectionRepository;
import reviewme.template.service.dto.response.SectionNameResponse;
import reviewme.template.service.dto.response.SectionNamesResponse;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    @Transactional(readOnly = true)
    public SectionNamesResponse getSectionNames(ReviewGroup reviewGroup) {
        List<SectionNameResponse> sectionNameResponses = sectionRepository.findAllByTemplateId(
                        reviewGroup.getTemplateId())
                .stream()
                .map(section -> new SectionNameResponse(section.getId(), section.getSectionName()))
                .toList();

        return new SectionNamesResponse(sectionNameResponses);
    }
}
