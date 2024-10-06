import { useEffect, useRef, useState } from 'react';

import DownArrowIcon from '@/assets/downArrow.svg';

import * as S from './styles';

interface DropdownProps {
  options: string[];
  selectedOption: string;
  handleSelect: (option: string) => void;
}

const Dropdown = ({ options, selectedOption, handleSelect }: DropdownProps) => {
  const [isOpened, setIsOpened] = useState(false);

  const dropdownRef = useRef<HTMLDivElement>(null);

  const handleDropdownButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  const handleOptionClick = (option: string) => {
    handleSelect(option);
    setIsOpened(false);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
      setIsOpened(false);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [dropdownRef]);

  return (
    <S.DropdownContainer ref={dropdownRef}>
      <S.DropdownButton onClick={handleDropdownButtonClick}>
        <span>{selectedOption}</span>
        <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
      </S.DropdownButton>
      {isOpened && (
        <S.OptionContainer>
          {options.map((option, index) => {
            return (
              <S.OptionItem key={index} id={option} onClick={(e) => handleOptionClick((e.target as HTMLLIElement).id)}>
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
