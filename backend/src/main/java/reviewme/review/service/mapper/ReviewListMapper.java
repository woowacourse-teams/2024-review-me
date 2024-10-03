package reviewme.review.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.OptionItem;
import reviewme.review.domain.CheckBoxAnswerSelectedOption;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.CheckBoxAnswerSelectedOptionRepository;
import reviewme.review.repository.CheckboxAnswerRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.repository.TextAnswerRepository;
import reviewme.review.service.dto.response.list.ReviewCategoryResponse;
import reviewme.review.service.dto.response.list.ReviewListElementResponse;
import reviewme.reviewgroup.domain.ReviewGroup;

@Component
@RequiredArgsConstructor
public class ReviewListMapper {

    public static final int CATEGORY_QUESTION_ID = 1;

    private final ReviewRepository reviewRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final CheckboxAnswerRepository checkboxAnswerRepository;
    private final CheckBoxAnswerSelectedOptionRepository checkBoxAnswerSelectedOptionRepository;
    private final TemplateCacheRepository templateCacheRepository;
    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    public List<ReviewListElementResponse> mapToReviewList(ReviewGroup reviewGroup, Long lastReviewId, int size) {
        List<Review> reviews = reviewRepository.findByReviewGroupIdWithLimit(reviewGroup.getId(), lastReviewId, size);
        List<Long> reviewIds = reviews.stream()
                .map(Review::getId)
                .toList();

        Map<Long, List<OptionItem>> categoryOptionItems = mapOptionItemsByReview(reviewIds);
        Map<Long, List<TextAnswer>> textAnswers = textAnswerRepository.findByReviewIds(reviewIds)
                .stream()
                .collect(Collectors.groupingBy(TextAnswer::getReviewId));

        return reviews.stream()
                .map(review -> mapToReviewListElementResponse(
                        review, categoryOptionItems.get(review.getId()), textAnswers.get(review.getId()))
                )
                .toList();
    }

    private Map<Long, List<OptionItem>> mapOptionItemsByReview(List<Long> reviewIds) {
        // 1. 리뷰들이 갖고있는 checkboxAnswer들 중 카테고리 질문에 해당하는 것들을 가져옵니다.
        List<CheckboxAnswer> checkboxAnswers = checkboxAnswerRepository.findAllByReviewIds(reviewIds)
                .stream()
                .filter(checkboxAnswer -> checkboxAnswer.getQuestionId() == CATEGORY_QUESTION_ID)
                .toList();

        // 2. checkboxAnswer들을 같은 review id인 것끼리 묶어서 Map으로 만듭니다.
        Map<Long, List<CheckboxAnswer>> checkboxAnswersByReviewId = checkboxAnswers.stream()
                .collect(Collectors.groupingBy(CheckboxAnswer::getReviewId));

        // 3. checkboxAnswer들의 id를 모아서, 해당 id로 CheckBoxAnswerSelectedOption들을 조회합니다.
        Map<Long, List<Long>> selectedOptionsByCheckBoxAnswer = getSelectedOptionsByCheckboxAnswerId(checkboxAnswers);

        // 4. 각 리뷰의 CheckboxAnswer에 해당하는 OptionItem들을 Map에 넣습니다.
        return checkboxAnswersByReviewId.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        reviewIdCheckboxAnswer -> mapOptionItemsForCheckboxAnswers(
                                reviewIdCheckboxAnswer.getValue(), selectedOptionsByCheckBoxAnswer
                        )
                ));
    }

    private Map<Long, List<Long>> getSelectedOptionsByCheckboxAnswerId(List<CheckboxAnswer> checkboxAnswers) {
        List<Long> checkboxAnswerIds = checkboxAnswers.stream()
                .map(CheckboxAnswer::getId)
                .toList();

        List<CheckBoxAnswerSelectedOption> checkBoxAnswerSelectedOptionGroup = checkBoxAnswerSelectedOptionRepository
                .findAllByCheckboxAnswerIds(checkboxAnswerIds);

        Map<Long, List<Long>> selectedOptionsByCheckBoxAnswerId = new HashMap<>();
        for (CheckBoxAnswerSelectedOption option : checkBoxAnswerSelectedOptionGroup) {
            selectedOptionsByCheckBoxAnswerId
                    .computeIfAbsent(option.getCheckboxAnswerId(), value -> new ArrayList<>())
                    .add(option.getSelectedOptionId());
        }
        return selectedOptionsByCheckBoxAnswerId;
    }

    private List<OptionItem> mapOptionItemsForCheckboxAnswers(List<CheckboxAnswer> checkboxAnswers,
                                                              Map<Long, List<Long>> selectedOptionsByCheckBoxAnswer) {
        List<OptionItem> optionItems = new ArrayList<>();
        for (CheckboxAnswer checkboxAnswer : checkboxAnswers) {
            List<Long> optionIds = selectedOptionsByCheckBoxAnswer.getOrDefault(checkboxAnswer.getId(),
                    Collections.emptyList());
            for (Long optionId : optionIds) {
                optionItems.add(templateCacheRepository.findOptionItem(optionId));
            }
        }
        return optionItems;
    }

    private ReviewListElementResponse mapToReviewListElementResponse(Review review,
                                                                     List<OptionItem> optionItems,
                                                                     List<TextAnswer> textAnswers) {
        List<ReviewCategoryResponse> categoryResponses = optionItems.stream()
                .map(optionItem -> new ReviewCategoryResponse(optionItem.getId(), optionItem.getContent()))
                .toList();

        return new ReviewListElementResponse(
                review.getId(),
                review.getCreatedDate(),
                reviewPreviewGenerator.generatePreview(textAnswers),
                categoryResponses
        );
    }
}
