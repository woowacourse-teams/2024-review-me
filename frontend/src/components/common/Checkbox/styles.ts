import styled from '@emotion/styled';

import { CheckboxStyleProps } from './index';

export const CheckboxContainer = styled.div<CheckboxStyleProps>`
  width: ${(props) => props.$style?.width || '2.8rem'};
  height: ${(props) => props.$style?.height || '2.8rem'};

  padding: 0;

  background-color: transparent;
  border: none;

  input {
    display: hidden;

    position: absolute;
    width: 0;
    height: 0;
  }
`;

export const CheckboxLabel = styled.label`
  cursor: pointer;
  object-fit: contain;

  img {
    width: 100%;
    height: 100%;
  }
`;
