import styled from '@emotion/styled';

interface AccordionStyleProps {
  $isOpened: boolean;
  $contentHeight?: number;
}

export const AccordionContainer = styled.div<AccordionStyleProps>`
  display: flex;
  flex-direction: column;
  gap: ${({ $isOpened }) => ($isOpened ? '2rem' : 0)};

  width: 100%;
  padding: 1rem;

  background-color: ${({ theme, $isOpened }) => ($isOpened ? theme.colors.white : theme.colors.lightGray)};
  border: 0.1rem solid ${({ theme }) => theme.colors.placeholder};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  &:hover {
    border: 0.1rem solid ${({ theme }) => theme.colors.primaryHover};
  }
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
  text-align: left;
  ::before {
    content: 'Q. ';
  }
`;

export const ArrowIcon = styled.img<AccordionStyleProps>`
  transform: ${({ $isOpened }) => ($isOpened ? 'rotate(180deg)' : 'rotate(0deg)')};
  transition: transform 0.3s ease-in-out;
`;

export const AccordionContentsWrapper = styled.div`
  overflow: hidden;
`;

export const AccordionContents = styled.div<AccordionStyleProps>`
  margin-top: ${({ $isOpened, $contentHeight }) => ($isOpened ? '0' : `-${$contentHeight}px`)};
  opacity: ${({ $isOpened }) => ($isOpened ? '1' : '0')};
  transition: 0.3s ease;
`;
