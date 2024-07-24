package reviewme.keyword.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.keyword.repository.KeywordRepository;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public List<KeywordResponse> findAllKeywords() {
        return keywordRepository.findAll()
                .stream()
                .map(keyword -> new KeywordResponse(keyword.getId(), keyword.getContent()))
                .toList();
    }
}
