package reviewme.template.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.dto.response.SectionNameResponse;
import reviewme.template.service.dto.response.SectionNamesResponse;

@ServiceTest
class SectionServiceTest {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    void 템플릿에_있는_섹션_이름_목록을_응답한다() {
        // given
        String sectionName1 = "섹션1";
        String sectionName2 = "섹션2";
        String sectionName3 = "섹션3";

        Section visibleSection1 = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(1L), null, sectionName1, "헤더", 1));
        Section visibleSection2 = sectionRepository.save(
                new Section(VisibleType.ALWAYS, List.of(2L), null, sectionName2, "헤더", 2));
        Section nonVisibleSection = sectionRepository.save(
                new Section(VisibleType.CONDITIONAL, List.of(1L), 1L, sectionName3, "헤더", 3));
        templateRepository.save(
                템플릿(List.of(nonVisibleSection.getId(), visibleSection2.getId(), visibleSection1.getId())));

        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when
        SectionNamesResponse actual = sectionService.getSectionNames(reviewGroup.getReviewRequestCode());

        // then
        assertThat(actual.sections()).extracting(SectionNameResponse::name)
                .containsExactly(sectionName1, sectionName2, sectionName3);
    }

    @Test
    void 템플릿에_있는_섹션_이름_목록_조회시_리뷰_요청_코드가_존재하지_않는_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when, then
        assertThatThrownBy(() -> sectionService.getSectionNames(
                reviewGroup.getReviewRequestCode() + "wrong"))
                .isInstanceOf(ReviewGroupNotFoundByReviewRequestCodeException.class);
    }
}
