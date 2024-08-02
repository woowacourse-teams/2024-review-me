package reviewme.review.controller.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class ContainsHeaderNameValidatorTest {

    private final ContainsHeaderValidator validator = new ContainsHeaderValidator();
    private final ContainsHeaderName validationAnnotation = mock(ContainsHeaderName.class);

    @BeforeEach
    void setUp() {
        given(validationAnnotation.headerName()).willReturn("test");
        validator.initialize(validationAnnotation);
    }

    @Test
    void 검증값이_헤더이름으로_존재하지_않으면_검증에_실패한다() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        validator.initialize(validationAnnotation);

        // When
        boolean result = validator.isValid(request, null);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void 검증값이_헤더에_존재하면_검증에_성공한다() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("test", "value");

        // when
        boolean result = validator.isValid(request, null);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 요청이_null인_경우_예외를_발생하지_않고_검증에_실패한다() {
        assertThat(validator.isValid(null, null)).isFalse();
    }
}
