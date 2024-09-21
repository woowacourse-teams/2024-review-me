package reviewme.review.service.module;

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
public class ReviewListMapperDBVer {

    private final ReviewRepository reviewRepository;
    private final OptionItemRepository optionItemRepository;

    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    public List<ReviewListElementResponse> mapToReviewList(ReviewGroup reviewGroup) {
        List<OptionItem> categoryOptionIds = optionItemRepository.findAllByOptionType(
                OptionType.CATEGORY); // 카테고리인 애들 5개가져왔음

        return reviewRepository.findAllByGroupId(reviewGroup.getId())
                .stream()
                .map(review -> mapToReviewListElementResponse(review, categoryOptionIds))
                .toList();
    }

    private ReviewListElementResponse mapToReviewListElementResponse(Review review,
                                                                     List<OptionItem> categoryOptionItems) {
        Set<Long> checkBoxOptionIds = review.getAllCheckBoxOptionIds(); // 선택한 옵션들 모음 최대 6개

        List<OptionItem> categoryOptionItemsByReview = categoryOptionItems.stream() // 강점카테고리인 "5"개 돌면서
                .filter(optionItem -> checkBoxOptionIds.contains(
                        optionItem.getId())) // 각각 선택옵션"6개" 돌면서 중에 강점5개에 해당하는 애 거르기
                .toList();

        List<ReviewCategoryResponse> categoryResponses = categoryOptionItemsByReview.stream() // 선택한 강점카테고리만이니까 1~"2"개 돌면서
                .map(optionItem -> new ReviewCategoryResponse(optionItem.getId(), optionItem.getContent()))
                .toList();

        return new ReviewListElementResponse(
                review.getId(),
                review.getCreatedDate(),
                reviewPreviewGenerator.generatePreview(review.getTextAnswers()),
                categoryResponses
        );
    }
}

