package reviewme.template.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;
import reviewme.template.service.exception.DefaultTemplateNotFoundException;

@ExtendWith(MockitoExtension.class)
class DefaultTemplateServiceTest {

    @InjectMocks
    private DefaultTemplateService defaultTemplateService;

    @Mock
    private TemplateRepository templateRepository;

    @Test
    void 기본_템플릿을_반환한다() {
        // given
        Template defaultTemplate = mock(Template.class);
        given(templateRepository.findById(anyLong()))
                .willReturn(Optional.of(defaultTemplate));

        // when
        Template actual = defaultTemplateService.getDefaultTemplate();

        // then
        assertThat(actual).isEqualTo(defaultTemplate);
    }

    @Test
    void 기본_템플릿이_없으면_예외가_발생한다() {
        // given
        given(templateRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatCode(() -> defaultTemplateService.getDefaultTemplate())
                .isInstanceOf(DefaultTemplateNotFoundException.class);
    }
}
