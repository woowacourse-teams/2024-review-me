package reviewme.review.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.dto.response.list.ReceivedReviewPageElementResponse;
import reviewme.review.service.dto.response.list.SelectedCategoryOptionResponse;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.OptionType;
import reviewme.template.repository.OptionItemRepository;

@Component
@RequiredArgsConstructor
public class ReceivedReviewMapper {

    private final OptionItemRepository optionItemRepository;
    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    public List<ReceivedReviewPageElementResponse> mapToReviewList(List<Review> reviews) {
        List<OptionItem> categoryOptionItems = optionItemRepository.findAllByOptionType(OptionType.CATEGORY);

        return reviews.stream()
                .map(review -> mapToElement(review, categoryOptionItems))
                .toList();
    }

    private ReceivedReviewPageElementResponse mapToElement(Review review,
                                                           List<OptionItem> categoryOptionItems) {
        return new ReceivedReviewPageElementResponse(
                review.getId(),
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
