package reviewme.review.service.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EitherTextOrCheckboxValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validRequests")
    void 유효한_응답에_대한_검증(String title, Long questionId, List<Long> selectedOptionIds, String text) {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(questionId, selectedOptionIds, text);

        // when
        Set<ConstraintViolation<ReviewAnswerRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    static Stream<Arguments> validRequests() {
        return Stream.of(
                Arguments.of("선택형 응답만 존재", 1L, List.of(1L), null),
                Arguments.of("서술형 응답만 존재", 1L, null, "답"),
                Arguments.of("필수 아닌 질문 (둘 다 없음)", 1L, null, "")
        );
    }

    @Test
    void 유효하지_않은_응답에_대한_검증() {
        // given
        ReviewAnswerRequest request = new ReviewAnswerRequest(1L, List.of(1L), "답");

        // when
        Set<ConstraintViolation<ReviewAnswerRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
    }
}
