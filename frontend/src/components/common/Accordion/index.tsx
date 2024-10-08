import DownArrowIcon from '@/assets/downArrow.svg';
import useAccordion from '@/hooks/useAccordion';
import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface AccordionProps {
  title: string;
  initiallyOpened?: boolean;
}

const Accordion = ({ title, initiallyOpened = false, children }: EssentialPropsWithChildren<AccordionProps>) => {
  const { isOpened, handleAccordionButtonClick } = useAccordion({ initiallyOpened });

  return (
    <S.AccordionContainer $isOpened={isOpened}>
      <S.AccordionButton onClick={handleAccordionButtonClick}>
        <S.AccordionTitle>{title}</S.AccordionTitle>
        <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
      </S.AccordionButton>
      {isOpened && <S.AccordionContents>{children}</S.AccordionContents>}
    </S.AccordionContainer>
  );
};

export default Accordion;
