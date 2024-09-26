package reviewme.review.service.mapper;

import java.util.List;
import java.util.Set;
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

    public List<ReviewListElementResponse> mapToReviewList(ReviewGroup reviewGroup, Long lastReviewId, int size) {
        List<OptionItem> categoryOptionItems = optionItemRepository.findAllByOptionType(OptionType.CATEGORY);
        return reviewRepository.findByReviewGroupIdWithLimit(reviewGroup.getId(), lastReviewId, size)
                .stream()
                .map(review -> mapToReviewListElementResponse(review, categoryOptionItems))
                .toList();
    }

    private ReviewListElementResponse mapToReviewListElementResponse(Review review,
                                                                     List<OptionItem> categoryOptionItems) {
        List<ReviewCategoryResponse> categoryResponses = mapToCategoryOptionResponse(review, categoryOptionItems);

        return new ReviewListElementResponse(
                review.getId(),
                review.getCreatedDate(),
                reviewPreviewGenerator.generatePreview(review.getTextAnswers()),
                categoryResponses
        );
    }

    private List<ReviewCategoryResponse> mapToCategoryOptionResponse(Review review,
                                                                     List<OptionItem> categoryOptionItems) {
        Set<Long> checkBoxOptionIds = review.getAllCheckBoxOptionIds();
        return categoryOptionItems.stream()
                .filter(optionItem -> checkBoxOptionIds.contains(optionItem.getId()))
                .map(optionItem -> new ReviewCategoryResponse(optionItem.getId(), optionItem.getContent()))
                .toList();
    }
}
