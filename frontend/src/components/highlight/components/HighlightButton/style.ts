import styled from '@emotion/styled';

import { HIGHLIGHT_BUTTON_WIDTH } from '@/constants';

export const Button = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: ${`${HIGHLIGHT_BUTTON_WIDTH / 10}rem`};
  padding: 0.5rem;

  border-radius: ${({ theme }) => theme.borderRadius.basic};

  &:hover {
    background-color: ${({ theme }) => theme.colors.lightPurple};
  }
`;
export const ButtonIcon = styled.img`
  width: 1.6rem;
  height: 1.6rem;
`;
