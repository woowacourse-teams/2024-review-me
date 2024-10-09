package reviewme.template.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
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
        Section visibleSection1 = sectionRepository.save(항상_보이는_섹션(List.of(1L), 1));
        Section visibleSection2 = sectionRepository.save(항상_보이는_섹션(List.of(2L), 2));
        Section nonVisibleSection = sectionRepository.save(조건부로_보이는_섹션(List.of(3L), 1L, 3));
        Template template = templateRepository.save(템플릿(
                List.of(nonVisibleSection.getId(), visibleSection2.getId(), visibleSection1.getId())));

        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when
        SectionNamesResponse actual = sectionService.getSectionNames(reviewGroup.getReviewRequestCode());

        // then
        assertThat(actual.sections()).extracting(SectionNameResponse::id)
                .containsExactly(visibleSection1.getId(), visibleSection2.getId(), nonVisibleSection.getId());
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
