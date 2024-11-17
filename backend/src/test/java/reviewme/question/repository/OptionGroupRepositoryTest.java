package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.Question;

@DataJpaTest
class OptionGroupRepositoryTest {

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 질문_아이디_그룹에_포함되는_모든_옵션_그룹을_불러온다() {
        // given
        Question question1 = questionRepository.save(선택형_필수_질문());
        Question question2 = questionRepository.save(선택형_필수_질문());
        Question question3 = questionRepository.save(선택형_필수_질문());

        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(question1.getId()));
        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(question1.getId()));
        OptionGroup optionGroup3 = optionGroupRepository.save(선택지_그룹(question2.getId()));
        OptionGroup optionGroup4 = optionGroupRepository.save(선택지_그룹(question2.getId()));
        OptionGroup optionGroup5 = optionGroupRepository.save(선택지_그룹(question3.getId()));
        OptionGroup optionGroup6 = optionGroupRepository.save(선택지_그룹(question3.getId()));


        // when
        List<OptionGroup> actual = optionGroupRepository.findAllByQuestionIds(
                List.of(question1.getId(), question2.getId()));

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionGroup1, optionGroup2, optionGroup3, optionGroup4);
    }
}
