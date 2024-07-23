import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { ButtonType } from '@/types/styles';

export const Button = styled.button<{ buttonType: ButtonType }>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 10rem;
  height: 4rem;
  padding: 2rem;

  border: 0.1rem solid ${({ theme }) => theme.colors.primary};
  border-radius: 0.8rem;

  ${({ theme, buttonType }) => {
    switch (buttonType) {
      case 'primary':
        return `
          color: ${theme.colors.white};
          background-color: ${theme.colors.primary};

          &:hover {
            background-color: ${theme.colors.primaryHover};
          }
        `;
      case 'secondary':
        return `
          color: ${theme.colors.primary};
          background-color: ${theme.colors.white};

          &:hover {
            background-color: ${theme.colors.lightPurple};
          }
        `;
      case 'disabled':
        return `
          pointer-events: none;
          color: ${theme.colors.disabledText};
          background-color: ${theme.colors.disabled};
          border-color: ${theme.colors.disabled};

          &:hover {
            background-color: ${theme.colors.disabled};
          }
        `;
      default:
        return css``;
    }
  }}
`;
