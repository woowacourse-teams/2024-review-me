import DownArrowIcon from '@/assets/downArrow.svg';
import useAccordion from '@/hooks/useAccordion';
import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface AccordionProps {
  title: string;
  isInitiallyOpened?: boolean;
}

const Accordion = ({ title, isInitiallyOpened = false, children }: EssentialPropsWithChildren<AccordionProps>) => {
  const { isOpened, contentHeight, contentRef, isFirstRender, handleAccordionButtonClick } = useAccordion({
    isInitiallyOpened,
  });

  return (
    <S.AccordionContainer $isOpened={isOpened}>
      <S.AccordionHeader $isOpened={isOpened}>
        <S.AccordionButton onClick={handleAccordionButtonClick}>
          <S.AccordionTitle>
            <S.QuestionMark>Q. </S.QuestionMark>
            {title}
          </S.AccordionTitle>
          <S.ArrowIcon src={DownArrowIcon} $isOpened={isOpened} alt="" />
        </S.AccordionButton>
      </S.AccordionHeader>
      <S.AccordionContentsWrapper>
        <S.AccordionContents
          $isOpened={isOpened}
          $contentHeight={contentHeight}
          $isFirstRender={isFirstRender}
          ref={contentRef}
        >
          {children}
        </S.AccordionContents>
      </S.AccordionContentsWrapper>
    </S.AccordionContainer>
  );
};

export default Accordion;
