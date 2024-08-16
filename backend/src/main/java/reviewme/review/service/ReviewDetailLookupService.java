package reviewme.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.TextAnswers;
import reviewme.review.domain.exception.InvalidReviewAccessByReviewGroupException;
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.detail.OptionGroupAnswerResponse;
import reviewme.review.service.dto.response.detail.OptionItemAnswerResponse;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.review.service.dto.response.detail.TemplateAnswerResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Section;
import reviewme.template.domain.exception.OptionGroupNotFoundByQuestionIdException;
import reviewme.template.repository.SectionRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReviewDetailLookupService {

    private final SectionRepository sectionRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final QuestionRepository questionRepository;
    private final OptionItemRepository optionItemRepository;
    private final OptionGroupRepository optionGroupRepository;

    public TemplateAnswerResponse getReviewDetail(String groupAccessCode, long reviewId) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByGroupAccessCode(groupAccessCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByGroupAccessCodeException(groupAccessCode));

        Review review = reviewRepository.findByIdAndReviewGroupId(reviewId, reviewGroup.getId())
                .orElseThrow(() -> new InvalidReviewAccessByReviewGroupException(reviewId, reviewGroup.getId()));
        long templateId = review.getTemplateId();

        Set<Long> selectedOptionItemIds = optionItemRepository.findSelectedOptionItemIdsByReviewId(reviewId);
        List<SectionAnswerResponse> sectionResponses = sectionRepository.findAllByTemplateId(templateId)
                .stream()
                .filter(section -> section.isVisibleBySelectedOptionIds(selectedOptionItemIds))
                .map(section -> getSectionAnswerResponse(review, section, reviewGroup))
                .toList();

        return new TemplateAnswerResponse(
                templateId,
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                review.getCreatedDate(),
                sectionResponses
        );
    }

    private SectionAnswerResponse getSectionAnswerResponse(Review review, Section section, ReviewGroup reviewGroup) {
        TextAnswers textAnswers = new TextAnswers(review.getTextAnswers());
        ArrayList<QuestionAnswerResponse> questionResponses = new ArrayList<>();

        for (Question question : questionRepository.findAllBySectionId(section.getId())) {
            if (question.isSelectable()) {
                questionResponses.add(getCheckboxAnswerResponse(review, question, reviewGroup));
                continue;
            }
            questionResponses.add(getTextAnswerResponse(textAnswers, question, reviewGroup));
        }

        return new SectionAnswerResponse(
                section.getId(),
                section.convertHeader("{revieweeName}", reviewGroup.getReviewee()),
                questionResponses
        );
    }

    private QuestionAnswerResponse getTextAnswerResponse(TextAnswers textAnswers, Question question,
                                                         ReviewGroup reviewGroup) {
        TextAnswer textAnswer = textAnswers.getAnswerByQuestionId(question.getId());
        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.convertContent("{revieweeName}", reviewGroup.getReviewee()),
                null,
                textAnswer.getContent()
        );
    }

    private QuestionAnswerResponse getCheckboxAnswerResponse(Review review, Question question,
                                                             ReviewGroup reviewGroup) {
        OptionGroup optionGroup = optionGroupRepository.findByQuestionId(question.getId())
                .orElseThrow(() -> new OptionGroupNotFoundByQuestionIdException(question.getId()));
        Set<Long> selectedOptionItemIds = optionItemRepository.findSelectedOptionItemIdsByReviewId(review.getId());
        List<OptionItemAnswerResponse> optionItemResponse =
                optionItemRepository.findSelectedOptionItemsByReviewIdAndQuestionId(review.getId(), question.getId())
                        .stream()
                        .map(optionItem -> new OptionItemAnswerResponse(
                                optionItem.getId(),
                                optionItem.getContent(),
                                selectedOptionItemIds.contains(optionItem.getId()))
                        ).toList();

        OptionGroupAnswerResponse optionGroupAnswerResponse = new OptionGroupAnswerResponse(
                optionGroup.getId(),
                optionGroup.getMinSelectionCount(),
                optionGroup.getMaxSelectionCount(),
                optionItemResponse
        );

        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.convertContent("{revieweeName}", reviewGroup.getReviewee()),
                optionGroupAnswerResponse,
                null
        );
    }
}
