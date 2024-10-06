import { useState } from 'react';

import DownArrowIcon from '@/assets/downArrow.svg';

import * as S from './styles';

interface DropdownProps {
  options: string[];
  selectedOption: string;
  handleSelect: (option: string) => void;
}

const Dropdown = ({ options, selectedOption, handleSelect }: DropdownProps) => {
  const [isOpened, setIsOpened] = useState(false);

  const handleDropdownButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  return (
    <S.DropdownContainer>
      <S.DropdownButton onClick={handleDropdownButtonClick}>
        <span>{selectedOption}</span>
        <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
      </S.DropdownButton>
      {isOpened && (
        <S.OptionContainer>
          {options.map((option, index) => {
            return (
              <S.OptionItem key={index} id={option} onClick={(e) => handleSelect((e.target as HTMLLIElement).id)}>
                {option}
              </S.OptionItem>
            );
          })}
        </S.OptionContainer>
      )}
    </S.DropdownContainer>
  );
};

export default Dropdown;
