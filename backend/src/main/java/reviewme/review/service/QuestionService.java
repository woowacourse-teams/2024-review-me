package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.review.dto.response.QuestionResponse;
import reviewme.review.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionResponse> findAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(question -> new QuestionResponse(question.getId(), question.getContent()))
                .toList();
    }
}
