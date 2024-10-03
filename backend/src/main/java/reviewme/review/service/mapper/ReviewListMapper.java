package reviewme.review.service.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCache;
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
//@RequiredArgsConstructor
public class ReviewListMapper {

    private final ReviewRepository reviewRepository;
    private final CheckboxAnswerRepository checkboxAnswerRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final CheckBoxAnswerSelectedOptionRepository checkBoxAnswerSelectedOptionRepository;
    private final Map<Long, OptionItem> optionItems;
    private final ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();

    public ReviewListMapper(ReviewRepository reviewRepository, CheckboxAnswerRepository checkboxAnswerRepository,
                            TextAnswerRepository textAnswerRepository,
                            CheckBoxAnswerSelectedOptionRepository checkBoxAnswerSelectedOptionRepository,
                            TemplateCache templateCache) {
        this.reviewRepository = reviewRepository;
        this.checkboxAnswerRepository = checkboxAnswerRepository;
        this.textAnswerRepository = textAnswerRepository;
        this.checkBoxAnswerSelectedOptionRepository = checkBoxAnswerSelectedOptionRepository;
        this.optionItems = templateCache.getOptionItems();
    }

    public List<ReviewListElementResponse> mapToReviewList(ReviewGroup reviewGroup, Long lastReviewId, int size) {
        List<Review> reviews = reviewRepository.findByReviewGroupIdWithLimit(reviewGroup.getId(), lastReviewId, size);
        // 리뷰 그룹에 해당하는 리뷰들의 id를 가져온다.
        List<Long> reviewIds = reviews.stream()
                .map(Review::getId)
                .toList();

        Map<Long, List<OptionItem>> optionItemsByReview = mapOptionItemsByReview(reviewIds);
        Map<Long, List<TextAnswer>> textAnswersByReview = textAnswerRepository.findByReviewIds(reviewIds)
                .stream()
                .collect(Collectors.groupingBy(TextAnswer::getReviewId));

        return reviews.stream()
                .map(review -> mapToReviewListElementResponse(review, optionItemsByReview, textAnswersByReview))
                .toList();
    }

    private Map<Long, List<OptionItem>> mapOptionItemsByReview(List<Long> reviewIds) {


        // 리뷰들이 갖고있는 checkboxAnswer들 중 카테고리 질문에 해당하는 것들을 가져온다.
        List<CheckboxAnswer> checkboxAnswers = checkboxAnswerRepository.findAllByReviewIds(reviewIds)
                .stream()
                .filter(checkboxAnswer -> checkboxAnswer.getQuestionId() == 1)
                .toList();

        // 카테고리checkboxAnswer들을 같은 review id인 것끼리 묶어 map으로 만든다.
        // Map <reviewid, list<checkboxanswer>>
        Map<Long, List<CheckboxAnswer>> checkboxAnswersByReviewId = checkboxAnswers.stream()
                .collect(Collectors.groupingBy(CheckboxAnswer::getReviewId));

        // checkboxAnswer들의 id들을 모은다.
        List<Long> checkboxAnswerIds = checkboxAnswers.stream()
                .map(CheckboxAnswer::getId)
                .toList();
        // checkboxAnswer의 id들을 갖고 checkBoxAnswerSelectedOption들을 조회해,
        List<CheckBoxAnswerSelectedOption> checkBoxAnswerSelectedOptionGroup = checkBoxAnswerSelectedOptionRepository
                .findAllByCheckboxAnswerIds(checkboxAnswerIds)
                .stream()
                .toList();
        // checkboxAnswerid, selectedOptionid 조합으로 map을 만든다.
        // Map<checkboxAnswerId, List<selectedOptionId>>
        Map<Long, List<Long>> checkBoxAnswerSelectedOptions = new HashMap<>();
        for (CheckBoxAnswerSelectedOption checkBoxAnswerSelectedOption : checkBoxAnswerSelectedOptionGroup) {
            if (checkBoxAnswerSelectedOptions.containsKey(checkBoxAnswerSelectedOption.getCheckboxAnswerId())) {
                List<Long> selectedOptionIds = checkBoxAnswerSelectedOptions.get(  // TODO: 예외처리 필요
                        checkBoxAnswerSelectedOption.getCheckboxAnswerId());
                selectedOptionIds.add(checkBoxAnswerSelectedOption.getSelectedOptionId());
            } else {
                checkBoxAnswerSelectedOptions.put(checkBoxAnswerSelectedOption.getCheckboxAnswerId(),
                        new ArrayList<>(List.of(checkBoxAnswerSelectedOption.getSelectedOptionId())));
            }
        }

        // checkboxAnswer를 하나씩 꺼내서 갖고있는 seletecOptionId를 list에 넣는다.
        // Map <reviewid, list<optionId>>
        Map<Long, List<OptionItem>> optionItemsByReview = new HashMap<>();

        for (Entry<Long, List<CheckboxAnswer>> checkboxAnswerByReview : checkboxAnswersByReviewId.entrySet()) {
            List<OptionItem> optionItemGroup = new ArrayList<>();
            List<CheckboxAnswer> checkboxAnswerGroup = checkboxAnswerByReview.getValue();
            for (CheckboxAnswer checkboxAnswer : checkboxAnswerGroup) {
                List<Long> optionIds = checkBoxAnswerSelectedOptions.get(checkboxAnswer.getId());  // TODO: 예외처리 필요
                for (long optionId : optionIds) {
                    optionItemGroup.add(optionItems.get(optionId));  // TODO: 예외처리 필요
                }
            }
            optionItemsByReview.put(checkboxAnswerByReview.getKey(), optionItemGroup);
        }
        return optionItemsByReview;
    }

    private ReviewListElementResponse mapToReviewListElementResponse(Review review,
                                                                     Map<Long, List<OptionItem>> optionItemsByReview,
                                                                     Map<Long, List<TextAnswer>> textAnswersByReview) {
        List<ReviewCategoryResponse> categoryResponses = optionItemsByReview.get(review.getId()) // TODO: 예외처리 필요
                .stream()
                .map(optionItem -> new ReviewCategoryResponse(optionItem.getId(), optionItem.getContent()))
                .toList();

        return new ReviewListElementResponse(
                review.getId(),
                review.getCreatedDate(),
                reviewPreviewGenerator.generatePreview(textAnswersByReview.get(review.getId())),
                categoryResponses
        );
    }
}
