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

    public List<ReviewListElementResponse> mapToReviewList(ReviewGroup reviewGroup) {
        List<OptionItem> categoryOptionIds = optionItemRepository.findAllByOptionType(OptionType.CATEGORY);
        return reviewRepository.findAllByGroupId(reviewGroup.getId())
                .stream()
                .map(review -> mapToReviewListElementResponse(review, categoryOptionIds))
                .toList();
    }

    public List<ReviewListElementResponse> mapToReviewListWithPagination(ReviewGroup reviewGroup,
                                                                         long lastReviewId, int limit) {
        List<OptionItem> categoryOptionIds = optionItemRepository.findAllByOptionType(OptionType.CATEGORY);
        return reviewRepository.findByReviewGroupIdWithPagination(reviewGroup.getId(), lastReviewId, limit)
                .stream()
                .map(review -> mapToReviewListElementResponse(review, categoryOptionIds))
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
