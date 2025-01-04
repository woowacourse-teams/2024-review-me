import styled from '@emotion/styled';

import { LoginButtonStyleProps } from './index';

export const ButtonLabelContainer = styled.div`
  display: flex;
  gap: 0.8rem;
  align-items: center;
  font-size: ${({ theme }) => theme.fontSize.basic};
`;

export const LogoImg = styled.img<Omit<LoginButtonStyleProps, '$style'>>`
  width: 3.2rem;
  height: 3.2rem;

  ${({ $logoStyle }) => $logoStyle && { ...$logoStyle }};
`;
