import styled from '@emotion/styled';

import { CheckboxStyleProps } from './index';

export const CheckboxContainer = styled.div<CheckboxStyleProps>`
  padding: 0;

  background-color: transparent;
  border: none;

  input {
    display: hidden;

    position: absolute;
    width: 0;
    height: 0;
  }
  ${({ $style }) => $style && { ...$style }};
`;

export const CheckboxLabel = styled.label`
  cursor: pointer;
  object-fit: contain;

  img {
    width: 100%;
    height: 100%;
  }
`;
