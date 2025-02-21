package reviewme.review.service.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.response.list.AuthoredReviewElementResponse;
import reviewme.review.service.dto.response.list.SelectedCategoryOptionResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.ReviewGroupNotFoundException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;
import reviewme.template.repository.OptionItemRepository;

@Component
@RequiredArgsConstructor
public class AuthoredReviewMapper {

    private final ReviewGroupRepository reviewGroupRepository;
    private final OptionItemRepository optionItemRepository;
    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    public List<AuthoredReviewElementResponse> mapToReviewList(List<Review> reviews) {
        Set<Long> reviewGroupIds = reviews.stream()
                .map(Review::getReviewGroupId)
                .collect(Collectors.toSet());
        Map<Long, ReviewGroup> reviewGroups = reviewGroupRepository.findAllByIds(reviewGroupIds).stream()
                .collect(Collectors.toMap(ReviewGroup::getId, rg -> rg));
        List<OptionItem> categoryOptionItems = optionItemRepository.findAllByOptionType(OptionType.CATEGORY);

        return reviews.stream()
                .map(review -> mapToElement(review, reviewGroups, categoryOptionItems))
                .toList();
    }

    private AuthoredReviewElementResponse mapToElement(Review review,
                                                       Map<Long, ReviewGroup> reviewGroups,
                                                       List<OptionItem> categoryOptionItems) {
        ReviewGroup reviewGroup = Optional.ofNullable(reviewGroups.get(review.getReviewGroupId()))
                .orElseThrow(() -> new ReviewGroupNotFoundException(review.getReviewGroupId()));

        return new AuthoredReviewElementResponse(
                review.getId(),
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                review.getCreatedAt(),
                reviewPreviewGenerator.generatePreview(review.getAnswersByType(TextAnswer.class)),
                mapToCategoryOptionResponse(review, categoryOptionItems)
        );
    }

    private List<SelectedCategoryOptionResponse> mapToCategoryOptionResponse(Review review,
                                                                               List<OptionItem> categoryOptionItems) {
        Set<Long> selectedOptionItems = getSelectedOptionItems(review);
        return categoryOptionItems.stream()
                .filter(optionItem -> selectedOptionItems.contains(optionItem.getId()))
                .map(optionItem -> new SelectedCategoryOptionResponse(optionItem.getId(), optionItem.getContent()))
                .toList();
    }

    private Set<Long> getSelectedOptionItems(Review review) {
        return review.getAnswersByType(CheckboxAnswer.class)
                .stream()
                .flatMap(answer -> answer.getSelectedOptionIds().stream())
                .map(CheckboxAnswerSelectedOption::getSelectedOptionId)
                .collect(Collectors.toSet());
    }
}
