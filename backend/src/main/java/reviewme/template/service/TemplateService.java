package reviewme.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.service.dto.response.TemplateResponse;
import reviewme.template.service.module.TemplateMapper;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final ReviewGroupRepository reviewGroupRepository;
    private final TemplateMapper templateMapper;

    @Transactional(readOnly = true)
    public TemplateResponse generateReviewForm(String reviewRequestCode) {
        ReviewGroup reviewGroup = findReviewGroupByRequestCodeOrThrow(reviewRequestCode);
        return templateMapper.mapToTemplateResponse(reviewGroup);
    }

    private ReviewGroup findReviewGroupByRequestCodeOrThrow(String reviewRequestCode) {
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }
}
