import styled from '@emotion/styled';

import { InputStyleProps } from './index';

export const Input = styled.input<InputStyleProps>`
  color: ${({ theme }) => theme.colors.black};

  border: 0.1rem solid ${({ theme }) => theme.colors.placeholder};
  border-radius: ${({ theme }) => theme.borderRadius.basic};

  padding: 1.2rem 1.6rem;

  font-size: 1.3rem;

  ::placeholder {
    color: ${({ theme }) => theme.colors.placeholder};
  }

  &:focus {
    border-color: ${({ theme }) => theme.colors.primaryHover};
  }

  ${({ $style }) => $style && { ...$style }}
`;
