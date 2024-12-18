package reviewme.template.domain.structured;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionItemFixture.선택지;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.exception.OptionItemNotExistException;
import reviewme.template.repository.OptionItemRepository;

@ServiceTest
class OptionItemsTest {

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Test
    void 존재하지_않는_옵션아이템으로_생성할_경우_예외가_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> new OptionItems(null))
                        .isInstanceOf(OptionItemNotExistException.class),
                () -> assertThatThrownBy(() -> new OptionItems(List.of()))
                        .isInstanceOf(OptionItemNotExistException.class)
        );
    }

    @Test
    void 옵션아이템ID들을_반환한다() {
        // given
        OptionItem optionItem1 = optionItemRepository.save(선택지(1L));
        OptionItem optionItem2 = optionItemRepository.save(선택지(1L));
        OptionItem optionItem3 = optionItemRepository.save(선택지(1L));

        OptionItems optionItems = new OptionItems(List.of(optionItem1, optionItem2, optionItem3));

        // when
        List<Long> actual = optionItems.getOptionItemIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionItem1.getId(), optionItem2.getId(), optionItem3.getId());
    }

    @Test
    void 옵션아이템의_옵션그룹ID들을_반환한다() {
        // given
        long optionGroupId1 = 1L;
        long optionGroupId2 = 2L;
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroupId1));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroupId1));
        OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroupId2));

        OptionItems optionItems = new OptionItems(List.of(optionItem1, optionItem2, optionItem3));

        // when
        Set<Long> actual = optionItems.getOptionGroupIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(optionGroupId1, optionGroupId2);
    }
}
