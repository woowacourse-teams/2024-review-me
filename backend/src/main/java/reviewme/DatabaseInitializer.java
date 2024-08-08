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

@Profile({"local", "dev"})
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final QuestionRepository questionRepository;
    private final KeywordRepository keywordRepository;

    @PostConstruct
    @Transactional
    void setup() {
        questionRepository.save(new Question("{revieweeName}을 한줄로 소개해주세요."));
        questionRepository.save(new Question("{revieweeName}으로부터 배우고 싶은 모습이 있나요?"));
        questionRepository.save(new Question("{revieweeName}이 없었더라면 우리 팀에는 어떤 점이 부족했을까요?"));
        questionRepository.save(new Question("{revieweeName}이 다음 목표로 어떤 것을 설정한다면 앞으로의 성장에 더 도움이 될까요?"));
        questionRepository.save(new Question("{revieweeName}에게 남기고 싶은 말이 있나요? 고맙거나 미안한 점, 응원하고 싶은 점 등 자유롭게 작성해주세요."));

        keywordRepository.save(new Keyword("회의를 잘 이끌어요"));
        keywordRepository.save(new Keyword("추진력이 좋아요"));
        keywordRepository.save(new Keyword("의견을 잘 조율해요"));
        keywordRepository.save(new Keyword("꼼꼼하게 기록해요"));
        keywordRepository.save(new Keyword("분위기를 밝게 만들어요"));
    }
}
