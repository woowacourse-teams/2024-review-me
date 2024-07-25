package reviewme.keyword.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.KeywordFixture;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class KeywordServiceTest {

    @Autowired
    KeywordService keywordService;

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void 모든_키워드를_조회한다() {
        // given
        keywordRepository.save(KeywordFixture.회의를_이끌어요.create());

        // when
        List<KeywordResponse> keywords = keywordService.findAllKeywords();

        // then
        assertThat(keywords).hasSize(1);
    }
}
