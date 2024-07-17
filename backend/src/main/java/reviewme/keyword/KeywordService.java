package reviewme.keyword;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.keyword.dto.response.KeywordsResponse;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public KeywordsResponse findAllKeywords() {
        List<KeywordResponse> responses = keywordRepository.findAll()
                .stream()
                .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getDetail()))
                .toList();
        return new KeywordsResponse(responses);
    }
}
