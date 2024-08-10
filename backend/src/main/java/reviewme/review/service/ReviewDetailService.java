package reviewme.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.Question2;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.Review2;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.TextAnswers;
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.repository.ReviewRepository2;
import reviewme.review.service.dto.response.detail.OptionGroupAnswerResponse;
import reviewme.review.service.dto.response.detail.OptionItemAnswerResponse;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.review.service.dto.response.detail.TemplateAnswerResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.template.domain.Section;
import reviewme.template.repository.SectionRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReviewDetailService {

    private final SectionRepository sectionRepository;
    private final ReviewRepository2 reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;
    private final QuestionRepository2 questionRepository;
    private final OptionItemRepository optionItemRepository;
    private final OptionGroupRepository optionGroupRepository;

    public TemplateAnswerResponse getReviewDetail(String groupAccessCode, long reviewId) {
        ReviewGroup reviewGroup = reviewGroupRepository.findByGroupAccessCode(groupAccessCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByGroupAccessCodeException(groupAccessCode));

        Review2 review = reviewRepository.findByIdAndReviewGroupId(reviewId, reviewGroup.getId())
                .orElseThrow(() -> new ReviewGroupNotFoundByGroupAccessCodeException(groupAccessCode));
        long templateId = review.getTemplateId();

        Set<Long> selectedOptionItemIds = optionItemRepository.findSelectedOptionItemIdsByReviewId(reviewId);
        List<SectionAnswerResponse> sectionResponses = sectionRepository.findAllByTemplateId(templateId)
                .stream()
                .filter(section -> section.isVisibleBySelectedOptionIds(selectedOptionItemIds))
                .map(section -> getSectionAnswerResponse(review, section))
                .toList();

        return new TemplateAnswerResponse(
                templateId,
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                sectionResponses
        );
    }

    private SectionAnswerResponse getSectionAnswerResponse(Review2 review, Section section) {
        TextAnswers textAnswers = new TextAnswers(review.getTextAnswers());
        ArrayList<QuestionAnswerResponse> questionResponses = new ArrayList<>();

        for (Question2 question : questionRepository.findAllBySectionId(section.getId())) {
            if (question.isSelectable()) {
                questionResponses.add(getCheckboxAnswerResponse(review, question));
                continue;
            }
            questionResponses.add(getTextAnswerResponse(question, textAnswers));
        }

        return new SectionAnswerResponse(
                section.getId(),
                section.getHeader(),
                questionResponses
        );
    }

    private QuestionAnswerResponse getTextAnswerResponse(Question2 question, TextAnswers textAnswers) {
        TextAnswer textAnswer = textAnswers.getAnswerByQuestionId(question.getId());
        return new QuestionAnswerResponse(
                question.getId(),
                question.isRequired(),
                question.getQuestionType(),
                question.getContent(),
                question.hasGuideline(),
                question.getGuideline(),
                null,
                textAnswer.getContent()
        );
    }

    private QuestionAnswerResponse getCheckboxAnswerResponse(Review2 review, Question2 question) {
        OptionGroup optionGroup = optionGroupRepository.getByQuestionId(question.getId());
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
                question.getContent(),
                question.hasGuideline(),
                question.getGuideline(),
                optionGroupAnswerResponse,
                null
        );
    }
}
