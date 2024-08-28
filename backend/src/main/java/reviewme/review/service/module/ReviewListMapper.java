package reviewme.review.service.module;

import java.util.List;
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

    private final OptionItemRepository optionItemRepository;
    private final ReviewRepository reviewRepository;

    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();


    public List<ReviewListElementResponse> mapToReviewList(ReviewGroup reviewGroup) {
        return reviewRepository.findReceivedReviewsByGroupId(reviewGroup.getId())
                .stream()
                .map(this::mapToReviewListElementResponse)
                .toList();
    }

    private ReviewListElementResponse mapToReviewListElementResponse(Review review) {
        List<OptionItem> categoryOptionItems = optionItemRepository.findByReviewIdAndOptionType(review.getId(),
                OptionType.CATEGORY);

        List<ReviewCategoryResponse> categoryResponses = categoryOptionItems.stream()
                .map(optionItem -> new ReviewCategoryResponse(optionItem.getId(), optionItem.getContent()))
                .toList();

        return new ReviewListElementResponse(
                review.getId(),
                review.getCreatedAt().toLocalDate(),
                reviewPreviewGenerator.generatePreview(review.getTextAnswers()),
                categoryResponses
        );
    }
}
