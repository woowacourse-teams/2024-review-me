package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import reviewme.fixture.QuestionFixure;
import reviewme.review.dto.response.QuestionResponse;
import reviewme.review.repository.QuestionRepository;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void 모든_리뷰_문항을_조회한다() {
        // given
        questionRepository.save(QuestionFixure.소프트스킬이_어떤가요.create());

        // when
        List<QuestionResponse> questions = questionService.findAllQuestions();

        // then
        assertThat(questions).hasSize(1);
    }
}
