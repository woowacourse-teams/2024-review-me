package reviewme.template.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.dto.response.QuestionResponse;
import reviewme.template.service.dto.response.SectionResponse;

@ServiceTest
class TemplateMapperTest {

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Test
    void 리뷰_그룹과_템플릿으로_템플릿_응답을_매핑한다() {
        // given
        Question question1 = 서술형_필수_질문();
        Question question2 = 서술형_필수_질문();
        Section section1 = 항상_보이는_섹션(List.of(question1));
        Section section2 = 항상_보이는_섹션(List.of(question2));
        Template template = templateRepository.save(new Template(List.of(section1, section2)));
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when
        List<SectionResponse> sectionResponses = templateMapper.mapSectionResponses(
                template.getId(), reviewGroup.getId()
        );

        // then
        assertAll(
                () -> assertThat(sectionResponses).hasSize(2),
                () -> assertThat(sectionResponses.get(0).header()).isEqualTo(section1.getHeader()),
                () -> assertThat(sectionResponses.get(0).questions()).hasSize(1),
                () -> assertThat(sectionResponses.get(1).header()).isEqualTo(section2.getHeader()),
                () -> assertThat(sectionResponses.get(1).questions()).hasSize(1)
        );
    }

    @Test
    void 섹션의_선택된_옵션이_필요없는_경우_제공하지_않는다() {
        // given
        Question question = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        Template template = templateRepository.save(new Template(List.of(section)));
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when
        List<SectionResponse> sectionResponses = templateMapper.mapSectionResponses(
                template.getId(), reviewGroup.getId()
        );

        // then
        assertThat(sectionResponses.get(0).onSelectedOptionId()).isNull();
    }

    @Test
    void 가이드라인이_없는_경우_가이드_라인을_제공하지_않는다() {
        // given
        Question question = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        Template template = templateRepository.save(new Template(List.of(section)));
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when
        List<SectionResponse> sectionResponses = templateMapper.mapSectionResponses(
                template.getId(), reviewGroup.getId()
        );

        // then
        QuestionResponse questionResponse = sectionResponses.get(0).questions().get(0);
        assertAll(
                () -> assertThat(questionResponse.hasGuideline()).isFalse(),
                () -> assertThat(questionResponse.guideline()).isNull()
        );
    }

    @Test
    void 옵션_그룹이_없는_질문의_경우_옵션_그룹을_제공하지_않는다() {
        // given
        Question question = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question));
        Template template = templateRepository.save(new Template(List.of(section)));

        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // when
        List<SectionResponse> sectionResponses = templateMapper.mapSectionResponses(
                template.getId(), reviewGroup.getId()
        );

        // then
        QuestionResponse questionResponse = sectionResponses.get(0).questions().get(0);
        assertThat(questionResponse.optionGroup()).isNull();
    }
}
