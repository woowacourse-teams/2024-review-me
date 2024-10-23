import styled from '@emotion/styled';

interface AccordionStyleProps {
  $isOpened: boolean;
  $contentHeight?: number;
  $isFirstRender?: boolean;
}

export const AccordionContainer = styled.div<AccordionStyleProps>`
  display: flex;
  flex-direction: column;
  gap: ${({ $isOpened }) => ($isOpened ? '1rem' : 0)};

  width: 100%;

  background-color: ${({ theme, $isOpened }) => ($isOpened ? theme.colors.white : theme.colors.lightGray)};
  border: 0.1rem solid ${({ theme }) => theme.colors.placeholder};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  &:hover {
    border: 0.1rem solid ${({ theme }) => theme.colors.primaryHover};
  }
`;

export const AccordionHeader = styled.div<AccordionStyleProps>`
  display: flex;
  padding: 1rem;
  border-bottom: ${({ $isOpened, theme }) => $isOpened && `0.1rem solid ${theme.colors.placeholder}`};
`;

export const AccordionButton = styled.button`
  display: flex;
  gap: 1rem;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: fit-content;
  min-height: 3rem;
`;

export const AccordionTitle = styled.p`
  display: flex;
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  text-align: left;
`;

export const QuestionMark = styled.p`
  margin-right: 0.5rem;
`;

export const ArrowIcon = styled.img<AccordionStyleProps>`
  transform: ${({ $isOpened }) => ($isOpened ? 'rotate(180deg)' : 'rotate(0deg)')};
  transition: transform 0.3s ease-in-out;
`;

export const AccordionContentsWrapper = styled.div`
  overflow: hidden;
`;

export const AccordionContents = styled.div<AccordionStyleProps>`
  margin-top: ${({ $isOpened, $contentHeight }) => ($isOpened ? 0 : `-${$contentHeight! * 0.1}rem`)};
  padding: 1rem;
  opacity: ${({ $isOpened }) => ($isOpened ? 1 : 0)};
  transition: ${({ $isFirstRender }) => ($isFirstRender === true ? 'none' : '0.3s ease-in')};
`;
