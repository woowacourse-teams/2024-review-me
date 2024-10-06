import DownArrowIcon from '@/assets/downArrow.svg';
import useDropdown from '@/hooks/useDropdown';

import * as S from './styles';

interface DropdownItem {
  text: string;
  value: string;
}

interface DropdownProps {
  items: DropdownItem[];
  selectedItem: string;
  handleSelect: (item: string) => void;
}

const Dropdown = ({ items, selectedItem: selectedOption, handleSelect }: DropdownProps) => {
  const { isOpened, handleDropdownButtonClick, handleOptionClick, dropdownRef } = useDropdown({ handleSelect });

  return (
    <S.DropdownContainer ref={dropdownRef}>
      <S.DropdownButton onClick={handleDropdownButtonClick}>
        <S.SelectedOption>{selectedOption}</S.SelectedOption>
        <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
      </S.DropdownButton>
      {isOpened && (
        <S.ItemContainer>
          {items.map((item) => {
            return (
              <S.DropdownItem key={item.value} onClick={() => handleOptionClick(item.value)}>
                {item.text}
              </S.DropdownItem>
            );
          })}
        </S.ItemContainer>
      )}
    </S.DropdownContainer>
  );
};

export default Dropdown;
