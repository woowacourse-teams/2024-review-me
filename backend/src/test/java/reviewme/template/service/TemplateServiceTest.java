package reviewme.template.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.ReviewGroupFixture.템플릿_지정_리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.dto.response.SectionNameResponse;
import reviewme.template.service.dto.response.SectionNamesResponse;
import reviewme.template.service.exception.TemplateNotFoundByReviewGroupException;

@ServiceTest
class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;
    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 리뷰이에게_작성될_리뷰_양식_생성_시_저장된_템플릿이_없을_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());
        String reviewRequestCode = reviewGroup.getReviewRequestCode();

        // when, then
        assertThatThrownBy(() -> templateService.generateReviewForm(reviewRequestCode))
                .isInstanceOf(TemplateNotFoundByReviewGroupException.class);
    }

    @Test
    void 템플릿에_있는_섹션_이름_목록을_응답한다() {
        // given
        Section section1 = new Section(VisibleType.ALWAYS, List.of(), null, "섹션1", "헤더", 1);
        Section section2 = new Section(VisibleType.ALWAYS, List.of(), null, "섹션2", "헤더", 2);
        Section section3 = new Section(VisibleType.CONDITIONAL, List.of(), null, "섹션3", "헤더", 3);
        Template template = templateRepository.save(new Template(List.of(section1, section2, section3)));
        ReviewGroup reviewGroup = reviewGroupRepository.save(템플릿_지정_리뷰_그룹(template.getId()));

        // when
        SectionNamesResponse actual = templateService.getSectionNames(reviewGroup);

        // then
        assertThat(actual.sections()).extracting(SectionNameResponse::name)
                .containsExactly("섹션1", "섹션2", "섹션3");
    }
}
