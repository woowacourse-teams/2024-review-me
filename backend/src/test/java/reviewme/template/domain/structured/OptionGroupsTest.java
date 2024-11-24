package reviewme.template.domain.structured;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.exception.OptionGroupNotExistException;
import reviewme.template.repository.OptionGroupRepository;

@ServiceTest
class OptionGroupsTest  {

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Test
    void 존재하지_않는_옵션그룹으로_생성할_경우_예외가_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> new OptionGroups(null))
                        .isInstanceOf(OptionGroupNotExistException.class),
                () -> assertThatThrownBy(() -> new OptionGroups(List.of()))
                        .isInstanceOf(OptionGroupNotExistException.class)
        );
    }

    @Test
    void 옵션그룹ID들을_반환한다() {
        // given
        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(1L));
        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(1L));
        OptionGroup optionGroup3 = optionGroupRepository.save(선택지_그룹(1L));

        OptionGroups optionGroups = new OptionGroups(List.of(optionGroup1, optionGroup2, optionGroup3));

        // when
        List<Long> actual = optionGroups.getOptionGroupIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionGroup1.getId(), optionGroup2.getId(), optionGroup3.getId());
    }

    @Test
    void 옵션그룹의_질문ID들을_반환한다() {
        // given
        long questionId1 = 1L;
        long questionId2 = 2L;
        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(questionId1));
        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(questionId2));
        OptionGroup optionGroup3 = optionGroupRepository.save(선택지_그룹(questionId2));

        OptionGroups optionGroups = new OptionGroups(List.of(optionGroup1, optionGroup2, optionGroup3));

        // when
        Set<Long> actual = optionGroups.getQuestionIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(questionId1, questionId2);
    }
}
