import styled from '@emotion/styled';

export const Button = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  &:hover {
    background-color: ${({ theme }) => theme.colors.primaryHover};
  }
`;
export const ButtonIcon = styled.img`
  width: 1.5rem;
  height: 1.5rem;
`;
