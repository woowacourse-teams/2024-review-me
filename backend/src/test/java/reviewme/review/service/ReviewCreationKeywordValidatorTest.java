package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.KeywordFixture.꼼꼼하게_기록해요;
import static reviewme.fixture.KeywordFixture.의견을_잘_조율해요;
import static reviewme.fixture.KeywordFixture.추진력이_좋아요;
import static reviewme.fixture.KeywordFixture.회의를_이끌어요;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.domain.exception.DuplicateKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewCreationKeywordValidatorTest {

    @Autowired
    ReviewCreationKeywordValidator reviewCreationKeywordValidator;

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void 중복되는_아이디의_키워드인지_검사한다() {
        // given
        Keyword keyword = keywordRepository.save(회의를_이끌어요.create());

        // when, then
        assertAll(
                () -> assertThatCode(() -> reviewCreationKeywordValidator.validate(List.of(keyword.getId())))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(
                        () -> reviewCreationKeywordValidator.validate(List.of(keyword.getId(), keyword.getId())))
                        .isInstanceOf(DuplicateKeywordException.class)
        );
    }

    @Test
    void 유효한_개수의_키워드인지_검사한다() {
        // given
        Keyword keyword1 = keywordRepository.save(회의를_이끌어요.create());
        Keyword keyword2 = keywordRepository.save(추진력이_좋아요.create());
        Keyword keyword3 = keywordRepository.save(꼼꼼하게_기록해요.create());
        Keyword keyword4 = keywordRepository.save(의견을_잘_조율해요.create());
        Keyword keyword5 = keywordRepository.save(new Keyword("간식을 잘 나눠줘요."));
        Keyword keyword6 = keywordRepository.save(new Keyword("감정을 잘 공감해줘요."));
        List<Long> keywordIds = List.of(
                keyword1.getId(), keyword2.getId(), keyword3.getId(), keyword4.getId(), keyword5.getId(),
                keyword6.getId()
        );

        // when, then
        assertThatCode(() -> reviewCreationKeywordValidator.validate(keywordIds))
                .isInstanceOf(KeywordLimitExceedException.class);
    }
}
