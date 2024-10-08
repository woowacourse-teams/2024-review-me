package reviewme.review.service.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionType;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
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
        return reviewRepository.findByReviewGroupIdWithLimit(reviewGroup.getId(), lastReviewId, size)
                .stream()
                .map(this::mapToReviewListElementResponse)
                .toList();
    }

    private ReviewListElementResponse mapToReviewListElementResponse(Review review) {
        List<ReviewCategoryResponse> categoryResponses = optionItemRepository.findByReviewIdAndOptionType(
                        review.getId(), OptionType.CATEGORY)
                .stream()
                .map(category -> new ReviewCategoryResponse(category.getId(), category.getContent()))
                .toList();

        List<TextAnswer> textAnswers = review.getAnswers()
                .stream()
                .filter(TextAnswer.class::isInstance)
                .map(TextAnswer.class::cast)
                .toList();

        return new ReviewListElementResponse(
                review.getId(),
                review.getCreatedDate(),
                reviewPreviewGenerator.generatePreview(textAnswers),
                categoryResponses
        );
    }
}
