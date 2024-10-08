import styled from '@emotion/styled';

export const AccordionContainer = styled.div<{ $isOpened: boolean }>`
  display: flex;
  flex-direction: column;
  gap: 2rem;

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
  height: 3rem;
`;

export const AccordionTitle = styled.p`
  ::before {
    content: 'Q. ';
  }
`;

export const ArrowIcon = styled.img<{ $isOpened: boolean }>`
  transform: ${({ $isOpened }) => ($isOpened ? 'rotate(180deg)' : 'rotate(0deg)')};
  transition: transform 0.3s ease-in-out;
`;

export const AccordionContents = styled.div`
  display: flex;
  flex-direction: column;
`;
