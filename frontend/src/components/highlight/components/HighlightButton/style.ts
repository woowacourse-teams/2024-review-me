import styled from '@emotion/styled';

import { HIGHLIGHT_BUTTON_SIZE } from '@/constants';
import { Position } from '@/types';

export const Button = styled.button<{ $position: Position; $width: number }>`
  position: absolute;
  top: ${(props) => props.$position.top};
  left: ${(props) => props.$position.left};

  display: flex;
  gap: 0.8rem;

  width: ${(props) => `${props.$width / 10}rem`};
  height: ${() => `${HIGHLIGHT_BUTTON_SIZE.height / 10}rem`};
  padding: 0.5rem 0.8rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
  -webkit-box-shadow: 0 0 ${() => `${HIGHLIGHT_BUTTON_SIZE.shadow / 10}rem`} -0.2rem #343434b8;
  box-shadow: 0 0 ${() => `${HIGHLIGHT_BUTTON_SIZE.shadow / 10}rem`} -0.2rem #343434b8;

  &:hover {
    background-color: ${({ theme }) => theme.colors.palePurple};
  }
`;

export const ButtonIcon = styled.img`
  width: 1.5rem;
  height: 1.5rem;
`;

export const Color = styled.div`
  width: 1.5rem;
  height: 1.5rem;
  background-color: ${({ theme }) => theme.colors.primary};
  border-radius: 50%;
`;
