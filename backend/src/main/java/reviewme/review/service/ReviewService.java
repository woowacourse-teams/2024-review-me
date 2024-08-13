package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review2;
import reviewme.review.domain.exception.CategoryOptionByReviewNotFoundException;
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.review.dto.response.ReceivedReviewCategoryResponse;
import reviewme.review.dto.response.ReceivedReviewResponse2;
import reviewme.review.dto.response.ReceivedReviewsResponse2;
import reviewme.review.repository.QuestionRepository;
import reviewme.review.repository.Review2Repository;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final QuestionRepository questionRepository;
    private final OptionItemRepository optionItemRepository;
    private final Review2Repository review2Repository;

    private final ReviewCreationQuestionValidator reviewCreationQuestionValidator;

    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    @Transactional(readOnly = true)
    public ReceivedReviewsResponse2 findReceivedReviews(String groupAccessCode) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByGroupAccessCode(groupAccessCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByGroupAccessCodeException(groupAccessCode));

        List<ReceivedReviewResponse2> reviewResponses =
                review2Repository.findReceivedReviewsByGroupId(reviewGroup.getId())
                        .stream()
                        .map(this::createReceivedReviewResponse)
                        .toList();

        return new ReceivedReviewsResponse2(reviewGroup.getReviewee(), reviewGroup.getProjectName(), reviewResponses);
    }

    private ReceivedReviewResponse2 createReceivedReviewResponse(Review2 review) {
        CheckboxAnswer checkboxAnswer = review.getCheckboxAnswers()
                .stream()
                .filter(answer -> optionItemRepository.existsByOptionTypeAndId(
                        OptionType.CATEGORY, answer.getSelectedOptionIds().get(0).getSelectedOptionId()
                ))
                .findFirst()
                .orElseThrow(() -> new CategoryOptionByReviewNotFoundException(review.getId()));

        List<ReceivedReviewCategoryResponse> categoryResponses =
                checkboxAnswer.getSelectedOptionIds()
                        .stream()
                        .map(checkBoxAnswerSelectedOptionId -> {
                            OptionItem optionItem = optionItemRepository.getOptionItemById(
                                    checkBoxAnswerSelectedOptionId.getSelectedOptionId()
                            );
                            return new ReceivedReviewCategoryResponse(
                                    optionItem.getId(), optionItem.getContent()
                            );
                        })
                        .toList();

        return new ReceivedReviewResponse2(
                review.getId(),
                review.getCreatedAt().toLocalDate(),
                reviewPreviewGenerator.generatePreview2(review.getTextAnswers()),
                categoryResponses
        );
    }
}
