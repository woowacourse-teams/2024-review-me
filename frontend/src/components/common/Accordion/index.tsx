import { useState } from 'react';

import DownArrowIcon from '@/assets/downArrow.svg';
import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface AccordionProps {
  title: string;
}

const Accordion = ({ title, children }: EssentialPropsWithChildren<AccordionProps>) => {
  const [isOpened, setIsOpened] = useState(false);

  const handleAccordionButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  return (
    <S.AccordionContainer $isOpened={isOpened}>
      <S.AccordionButton onClick={handleAccordionButtonClick}>
        <S.AccordionTitle>{title}</S.AccordionTitle>
        <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
      </S.AccordionButton>
      {isOpened && <S.AccordionContents $isOpened={isOpened}>{children}</S.AccordionContents>}
    </S.AccordionContainer>
  );
};

export default Accordion;
