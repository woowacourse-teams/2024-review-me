import styled from '@emotion/styled';

import { CheckboxStyleProps } from './index';

export const CheckboxContainer = styled.div<CheckboxStyleProps>`
  width: 2.7rem;
  height: 2.7rem;
  padding: 0;

  background-color: transparent;
  border: none;

  input {
    position: absolute;

    clip-path: inset(1px);
  }

  ${(props) => props.$isReadonly && 'pointer-events: none;'}
  ${({ $style }) => $style && { ...$style }};
`;

export const CheckboxLabel = styled.label`
  cursor: pointer;
  display: inline-block;
  width: 2.7rem;
  height: 2.7rem;
`;
