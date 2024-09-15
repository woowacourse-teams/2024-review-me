package reviewme.review.service.module;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReviewCategoryResponse;
import reviewme.review.service.dto.response.list.ReviewListElementResponse;
import reviewme.reviewgroup.domain.ReviewGroup;

@Component
@RequiredArgsConstructor
public class ReviewListMapper {

    private final ReviewRepository reviewRepository;
    private final OptionItemRepository optionItemRepository;

    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    public List<ReviewListElementResponse> mapToReviewList(ReviewGroup reviewGroup) {
        Map<Long, OptionItem> categoryOptionItems = optionItemRepository.findAllByOptionType(OptionType.CATEGORY)
                .stream()
                .collect(Collectors.toMap(OptionItem::getId, optionItem -> optionItem));

        return reviewRepository.findAllByGroupId(reviewGroup.getId())
                .stream()
                .map(review -> mapToReviewListElementResponse(review, categoryOptionItems))
                .toList();
    }

    private ReviewListElementResponse mapToReviewListElementResponse(Review review,
                                                                     Map<Long, OptionItem> categoryOptionItems) {
        Set<Long> checkBoxOptionIds = review.getAllCheckBoxOptionIds();

        List<ReviewCategoryResponse> categoryResponses = checkBoxOptionIds.stream()
                .filter(categoryOptionItems::containsKey)
                .map(optionItemId -> {
                    OptionItem optionItem = categoryOptionItems.get(optionItemId);
                    return new ReviewCategoryResponse(optionItem.getId(), optionItem.getContent());
                })
                .toList();

        return new ReviewListElementResponse(
                review.getId(),
                review.getCreatedDate(),
                reviewPreviewGenerator.generatePreview(review.getTextAnswers()),
                categoryResponses
        );
    }
}
