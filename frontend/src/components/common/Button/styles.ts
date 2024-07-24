import styled from '@emotion/styled';

import { ButtonType } from '@/types/styles';

export const Button = styled.button<{ buttonType: ButtonType }>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 10rem;
  height: 4rem;
  padding: 1rem 2rem;

  color: ${({ theme, buttonType }) => (buttonType === 'primary' ? theme.colors.white : theme.colors.pri)};

  background-color: ${({ theme, buttonType }) =>
    buttonType === 'primary' ? theme.colors.primary : theme.colors.white};
  border: 0.1rem solid ${({ theme }) => theme.colors.primary};
  border-radius: 0.8rem;

  &:hover {
    color: ${({ theme }) => theme.colors.black};
    background-color: ${({ theme, buttonType }) => (buttonType ? theme.colors.primaryHover : theme.colors.lightGray)};
  }
`;
