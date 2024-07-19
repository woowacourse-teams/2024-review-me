import styled from '@emotion/styled';

import { ButtonType } from '@/types/styles';

export const Button = styled.button<{ buttonType: ButtonType }>`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 10rem;
  height: 4rem;

  background-color: ${({ theme, buttonType }) =>
    buttonType === 'primary' ? theme.colors.primary : theme.colors.white};
  color: ${({ theme, buttonType }) => (buttonType === 'primary' ? theme.colors.white : theme.colors.pri)};

  border: 0.1rem solid ${({ theme }) => theme.colors.primary};
  border-radius: 0.8rem;
  padding: 2rem;

  &:hover {
    background-color: ${({ theme, buttonType }) => (buttonType ? theme.colors.primaryHover : theme.colors.lightGray)};\
    color: ${({ theme }) => theme.colors.black}
  }
`;
