package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문1_선택지1;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문1_선택지2;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문2_선택지2;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문_서술형1;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문_서술형2;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문_선택형1;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문_선택형2;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문_섹션1;
import static reviewme.support.TestDataInitializer.저장된_꼬리_질문_섹션2;
import static reviewme.support.TestDataInitializer.저장된_단점_보완_섹션;
import static reviewme.support.TestDataInitializer.저장된_단점_보완_질문_서술형;
import static reviewme.support.TestDataInitializer.저장된_응원_질문_서술형;
import static reviewme.support.TestDataInitializer.저장된_응원_질문_섹션;
import static reviewme.support.TestDataInitializer.저장된_카테고리_선택지1;
import static reviewme.support.TestDataInitializer.저장된_카테고리_선택지2;
import static reviewme.support.TestDataInitializer.저장된_카테고리_섹션;
import static reviewme.support.TestDataInitializer.저장된_카테고리_질문_선택형;
import static reviewme.support.TestDataInitializer.저장된_템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.InvalidReviewAccessByReviewGroupException;
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.detail.QuestionAnswerResponse;
import reviewme.review.service.dto.response.detail.SectionAnswerResponse;
import reviewme.review.service.dto.response.detail.TemplateAnswerResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.TestDataInitializer;
import reviewme.support.ServiceTest;

@ServiceTest
@Import(TestDataInitializer.class)
class ReviewDetailLookupServiceTest {

    @Autowired
    private ReviewDetailLookupService reviewDetailLookupService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TestDataInitializer testDataInitializer;

    @Test
    void 잘못된_그룹_액세스_코드로_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹.create());

        Review review = reviewRepository.save(new Review(0, reviewGroup.getId(), List.of(), List.of()));

        // when, then
        String wrongGroupAccessCode = "wrong" + reviewGroup.getGroupAccessCode();
        assertThatThrownBy(() -> reviewDetailLookupService.getReviewDetail(wrongGroupAccessCode, review.getId()))
                .isInstanceOf(ReviewGroupNotFoundByGroupAccessCodeException.class);
    }

    @Test
    void 리뷰_그룹에_해당하지_않는_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        ReviewGroup tedReviewGroup = reviewGroupRepository.save(리뷰_그룹.createWithGroupAccessCode("ted"));
        ReviewGroup aruReviewGroup = reviewGroupRepository.save(리뷰_그룹.createWithGroupAccessCode("aru"));

        Review aruReview = reviewRepository.save(new Review(0, aruReviewGroup.getId(), List.of(), List.of()));

        // when, then
        assertThatThrownBy(
                () -> reviewDetailLookupService.getReviewDetail(tedReviewGroup.getGroupAccessCode(), aruReview.getId()))
                .isInstanceOf(InvalidReviewAccessByReviewGroupException.class);
    }

    @Test
    void 사용자가_작성한_리뷰를_확인한다() {
        // given
        testDataInitializer.saveTemplateRelatedData();

        ReviewGroup savedReviewGroup = reviewGroupRepository.save(리뷰_그룹.create());
        Review savedReview = reviewRepository.save(new Review(
                저장된_템플릿.getId(),
                savedReviewGroup.getId(),
                List.of(new TextAnswer(저장된_꼬리_질문_서술형1.getId(), "꼬리 질문 서술형 답변1"),
                        new TextAnswer(저장된_꼬리_질문_서술형2.getId(), "꼬리 질문 서술형 답변2"),
                        new TextAnswer(저장된_단점_보완_질문_서술형.getId(), "단점 보완 답변"),
                        new TextAnswer(저장된_응원_질문_서술형.getId(), "응원 문구")),
                List.of(new CheckboxAnswer(저장된_카테고리_질문_선택형.getId(),
                                List.of(저장된_카테고리_선택지1.getId(), 저장된_카테고리_선택지2.getId())),
                        new CheckboxAnswer(저장된_꼬리_질문_선택형1.getId(), List.of(저장된_꼬리_질문1_선택지1.getId())),
                        new CheckboxAnswer(저장된_꼬리_질문_선택형2.getId(), List.of(저장된_꼬리_질문2_선택지2.getId()))
                ))
        );

        // when
        TemplateAnswerResponse reviewDetail
                = reviewDetailLookupService.getReviewDetail(savedReviewGroup.getGroupAccessCode(), savedReview.getId());

        // then
        assertAll(
                () -> assertThat(reviewDetail.projectName()).isEqualTo(savedReviewGroup.getProjectName()),
                () -> assertThat(reviewDetail.revieweeName()).isEqualTo(savedReviewGroup.getReviewee()),
                () -> assertThat(reviewDetail.formId()).isEqualTo(저장된_템플릿.getId()),
                () -> assertThat(reviewDetail.sections())
                        .extracting(SectionAnswerResponse::sectionId)
                        .containsExactlyInAnyOrder(
                                저장된_카테고리_섹션.getId(),
                                저장된_꼬리_질문_섹션1.getId(), 저장된_꼬리_질문_섹션2.getId(),
                                저장된_단점_보완_섹션.getId(), 저장된_응원_질문_섹션.getId()
                        ),
                () -> assertThat(reviewDetail.sections())
                        .flatExtracting(SectionAnswerResponse::questions)
                        .extracting(QuestionAnswerResponse::questionId)
                        .containsExactlyInAnyOrder(
                                저장된_카테고리_질문_선택형.getId(),
                                저장된_꼬리_질문_선택형1.getId(), 저장된_꼬리_질문_서술형1.getId(),
                                저장된_꼬리_질문_선택형2.getId(), 저장된_꼬리_질문_서술형2.getId(),
                                저장된_단점_보완_질문_서술형.getId(), 저장된_응원_질문_서술형.getId()
                        )
        );
    }

    @Test
    void 섹션을_보이게_하는_옵션을_선택하지_않은_경우_해당_섹션을_제외하고_보여준다() {
        // given
        testDataInitializer.saveTemplateRelatedData();

        ReviewGroup savedReviewGroup = reviewGroupRepository.save(리뷰_그룹.create());
        Review savedReview = reviewRepository.save(new Review(
                저장된_템플릿.getId(),
                savedReviewGroup.getId(),
                List.of(new TextAnswer(저장된_꼬리_질문_서술형1.getId(), "꼬리 질문 서술형 답변1"),
                        new TextAnswer(저장된_단점_보완_질문_서술형.getId(), "단점 보완 답변"),
                        new TextAnswer(저장된_응원_질문_서술형.getId(), "응원 문구")
                ),
                List.of(new CheckboxAnswer(저장된_카테고리_질문_선택형.getId(), List.of(저장된_카테고리_선택지1.getId())),
                        new CheckboxAnswer(저장된_꼬리_질문_선택형1.getId(), List.of(저장된_꼬리_질문1_선택지2.getId()))))
        );

        // when
        TemplateAnswerResponse reviewDetail
                = reviewDetailLookupService.getReviewDetail(savedReviewGroup.getGroupAccessCode(), savedReview.getId());
        // then
        assertAll(
                () -> assertThat(reviewDetail.projectName()).isEqualTo(savedReviewGroup.getProjectName()),
                () -> assertThat(reviewDetail.revieweeName()).isEqualTo(savedReviewGroup.getReviewee()),
                () -> assertThat(reviewDetail.formId()).isEqualTo(저장된_템플릿.getId()),
                () -> assertThat(reviewDetail.sections())
                        .extracting(SectionAnswerResponse::sectionId)
                        .containsExactlyInAnyOrder(
                                저장된_카테고리_섹션.getId(),
                                저장된_꼬리_질문_섹션1.getId(),
                                저장된_단점_보완_섹션.getId(), 저장된_응원_질문_섹션.getId()
                        ),
                () -> assertThat(reviewDetail.sections())
                        .flatExtracting(SectionAnswerResponse::questions)
                        .extracting(QuestionAnswerResponse::questionId)
                        .containsExactlyInAnyOrder(
                                저장된_카테고리_질문_선택형.getId(),
                                저장된_꼬리_질문_선택형1.getId(), 저장된_꼬리_질문_서술형1.getId(),
                                저장된_단점_보완_질문_서술형.getId(), 저장된_응원_질문_서술형.getId()
                        )
        );
    }
}
