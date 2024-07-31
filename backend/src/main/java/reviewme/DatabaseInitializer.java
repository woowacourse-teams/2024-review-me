package reviewme;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.question.domain.Question;
import reviewme.review.repository.QuestionRepository;

@Profile("local")
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final QuestionRepository questionRepository;
    private final KeywordRepository keywordRepository;

    @PostConstruct
    @Transactional
    void setup() {
        Question question1 = questionRepository.save(new Question("동료의 개발 역량 향상을 위해 피드백을 남겨 주세요."));
        Question question2 = questionRepository.save(new Question("동료의 소프트 스킬의 성장을 위해 피드백을 남겨 주세요."));

        Keyword keyword1 = keywordRepository.save(new Keyword("회의를 잘 이끌어요"));
        Keyword keyword2 = keywordRepository.save(new Keyword("추진력이 좋아요"));
        Keyword keyword3 = keywordRepository.save(new Keyword("의견을 잘 조율해요"));
        Keyword keyword4 = keywordRepository.save(new Keyword("꼼꼼하게 기록해요"));
        Keyword keyword5 = keywordRepository.save(new Keyword("말투가 상냥해요"));
    }
}
