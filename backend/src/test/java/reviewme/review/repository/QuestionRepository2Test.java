package reviewme.review.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.template.domain.Section;
import reviewme.template.domain.VisibleType;
import reviewme.template.repository.SectionRepository;

@DataJpaTest
class QuestionRepository2Test {

    @Autowired
    private QuestionRepository2 questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    void 섹션_아이디로_질문_목록을_순서대로_가져온다() {
        // given
        Question2 question1 = questionRepository.save(new Question2(true, QuestionType.TEXT, "질문1", null, 1));
        Question2 question2 = questionRepository.save(new Question2(true, QuestionType.TEXT, "질문2", null, 2));
        Question2 question3 = questionRepository.save(new Question2(true, QuestionType.TEXT, "질문3", null, 3));
        questionRepository.save(new Question2(true, QuestionType.TEXT, "질문4", null, 1));

        List<Long> questionIds = List.of(question3.getId(), question1.getId(), question2.getId());
        Section section = sectionRepository.save(new Section(VisibleType.ALWAYS, questionIds, null, "header", 0));

        // when
        List<Question2> actual = questionRepository.findAllBySectionId(section.getId());

        // then
        assertThat(actual).extracting(Question2::getId)
                .containsExactly(question1.getId(), question2.getId(), question3.getId());
    }
}
