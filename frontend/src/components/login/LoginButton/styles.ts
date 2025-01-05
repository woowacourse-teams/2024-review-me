import styled from '@emotion/styled';

import { LoginButtonStyleProps } from './index';

export const ButtonLabelContainer = styled.div`
  display: flex;
  gap: 0.8rem;
  align-items: center;
`;

export const LogoImg = styled.img<Pick<LoginButtonStyleProps, '$logoImgStyle'>>`
  width: 3.2rem;
  height: 3.2rem;

  ${({ $logoImgStyle }) => $logoImgStyle && { ...$logoImgStyle }};
`;
