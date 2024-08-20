package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewCategoryResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewResponse;
import reviewme.review.service.dto.response.list.ReceivedReviewsResponse;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByCodesException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewGroupRepository reviewGroupRepository;
    private final OptionItemRepository optionItemRepository;
    private final ReviewRepository reviewRepository;

    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse findReceivedReviews(String reviewRequestCode, String groupAccessCode) {
        ReviewGroup reviewGroup = reviewGroupRepository
                .findByReviewRequestCodeAndGroupAccessCode_Code(reviewRequestCode, groupAccessCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByCodesException(reviewRequestCode, groupAccessCode));

        List<ReceivedReviewResponse> reviewResponses =
                reviewRepository.findReceivedReviewsByGroupId(reviewGroup.getId())
                        .stream()
                        .map(this::createReceivedReviewResponse)
                        .toList();

        return new ReceivedReviewsResponse(reviewGroup.getReviewee(), reviewGroup.getProjectName(), reviewResponses);
    }

    private ReceivedReviewResponse createReceivedReviewResponse(Review review) {
        List<OptionItem> categoryOptionItems = optionItemRepository.findByReviewIdAndOptionType(review.getId(),
                OptionType.CATEGORY);

        List<ReceivedReviewCategoryResponse> categoryResponses = categoryOptionItems.stream()
                .map(optionItem -> new ReceivedReviewCategoryResponse(optionItem.getId(), optionItem.getContent()))
                .toList();

        return new ReceivedReviewResponse(
                review.getId(),
                review.getCreatedAt().toLocalDate(),
                reviewPreviewGenerator.generatePreview2(review.getTextAnswers()),
                categoryResponses
        );
    }
}
