package reviewme.reviewgroup.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RandomCodeGeneratorTest {

    @Test
    void 주어진_길이에_맞는_랜덤한_문자열을_생성한다() {
        // given
        int length = 8;
        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();

        // when
        String actual = randomCodeGenerator.generate(length);

        // then
        assertThat(actual).matches("[a-zA-Z0-9]{%d}".formatted(length));
    }
}
