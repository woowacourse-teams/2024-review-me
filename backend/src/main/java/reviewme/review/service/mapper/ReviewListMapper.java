package reviewme.review.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.NewCheckboxAnswer;
import reviewme.review.domain.NewCheckboxAnswerSelectedOption;
import reviewme.review.domain.NewReview;
import reviewme.review.domain.NewTextAnswer;
import reviewme.review.repository.NewReviewRepository;
import reviewme.review.service.dto.response.list.ReviewCategoryResponse;
import reviewme.review.service.dto.response.list.ReviewListElementResponse;
import reviewme.reviewgroup.domain.ReviewGroup;

@Component
@RequiredArgsConstructor
public class ReviewListMapper {

    private final NewReviewRepository reviewRepository;
    private final OptionItemRepository optionItemRepository;

    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    public List<ReviewListElementResponse> mapToReviewList(ReviewGroup reviewGroup, Long lastReviewId, int size) {
        List<OptionItem> categoryOptionItems = optionItemRepository.findAllByOptionType(OptionType.CATEGORY);
        return reviewRepository.findByReviewGroupIdWithLimit(reviewGroup.getId(), lastReviewId, size)
                .stream()
                .map(review -> mapToReviewListElementResponse(review, categoryOptionItems))
                .toList();
    }

    private ReviewListElementResponse mapToReviewListElementResponse(NewReview review,
                                                                     List<OptionItem> categoryOptionItems) {
        List<ReviewCategoryResponse> categoryResponses = mapToCategoryOptionResponse(review, categoryOptionItems);

        return new ReviewListElementResponse(
                review.getId(),
                review.getCreatedDate(),
                reviewPreviewGenerator.generatePreview(review.getAnswersByType(NewTextAnswer.class)),
                categoryResponses
        );
    }

    private List<ReviewCategoryResponse> mapToCategoryOptionResponse(NewReview review,
                                                                     List<OptionItem> categoryOptionItems) {
        Set<Long> checkBoxOptionIds = review.getAnswersByType(NewCheckboxAnswer.class)
                .stream()
                .flatMap(answer -> answer.getSelectedOptionIds().stream())
                .map(NewCheckboxAnswerSelectedOption::getSelectedOptionId)
                .collect(Collectors.toSet());
        return categoryOptionItems.stream()
                .filter(optionItem -> checkBoxOptionIds.contains(optionItem.getId()))
                .map(optionItem -> new ReviewCategoryResponse(optionItem.getId(), optionItem.getContent()))
                .toList();
    }
}
