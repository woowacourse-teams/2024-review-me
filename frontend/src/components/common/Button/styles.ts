import { css, Theme } from '@emotion/react';
import styled from '@emotion/styled';

import { ButtonType } from '@/types';

const primaryStyle = (theme: Theme) => css`
  color: ${theme.colors.white};
  background-color: ${theme.colors.primary};

  &:hover {
    background-color: ${theme.colors.primaryHover};
    border: 0.1rem solid ${theme.colors.primaryHover};
  }
`;

const secondaryStyle = (theme: Theme) => css`
  color: ${theme.colors.primary};
  background-color: ${theme.colors.white};

  &:hover {
    background-color: ${theme.colors.lightPurple};
  }
`;

const disabledStyle = (theme: Theme) => css`
  pointer-events: none;
  color: ${theme.colors.disabledText};
  background-color: ${theme.colors.disabled};
  border-color: ${theme.colors.disabled};

  &:hover {
    background-color: ${theme.colors.disabled};
  }
`;

const getButtonStyle = (buttonType: ButtonType, theme: Theme) => {
  switch (buttonType) {
    case 'primary':
      return primaryStyle(theme);
    case 'secondary':
      return secondaryStyle(theme);
    case 'disabled':
      return disabledStyle(theme);
    default:
      return css``;
  }
};

interface ButtonProps {
  buttonType: ButtonType;
  $style?: React.CSSProperties;
}

export const Button = styled.button<ButtonProps>`
  display: flex;
  align-items: center;
  justify-content: center;

  padding: 1rem 2rem;

  border: 0.1rem solid ${({ theme }) => theme.colors.primary};
  border-radius: 0.8rem;

  ${({ $style }) => $style && { ...$style }};
  ${({ buttonType, theme }) => getButtonStyle(buttonType, theme)};
`;

export const Image = styled.img`
  margin-right: 0.8rem;
`;
