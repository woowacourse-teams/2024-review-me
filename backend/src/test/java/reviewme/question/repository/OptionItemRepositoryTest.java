package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question;

@DataJpaTest
class OptionItemRepositoryTest {

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 옵션_타입에_해당하는_모든_옵션_아이템을_불러온다() {
        // given
        Question question = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question.getId()));

        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId()));

        // when
        List<OptionItem> actual = optionItemRepository.findAllByOptionType(OptionType.CATEGORY);

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionItem1, optionItem2);
    }

    @Test
    void 질문_아이디_그룹에_포함되는_모든_옵션_아이템을_불러온다() {
        // given
        Question question1 = questionRepository.save(선택형_필수_질문());
        Question question2 = questionRepository.save(선택형_필수_질문());
        Question question3 = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(question1.getId()));
        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(question2.getId()));
        OptionGroup optionGroup3 = optionGroupRepository.save(선택지_그룹(question3.getId()));

        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));
        OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));
        OptionItem optionItem4 = optionItemRepository.save(선택지(optionGroup3.getId()));

        // when
        List<OptionItem> actual = optionItemRepository.findAllByQuestionIds(
                List.of(question1.getId(), question2.getId()));

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionItem1, optionItem2, optionItem3);
    }
}
