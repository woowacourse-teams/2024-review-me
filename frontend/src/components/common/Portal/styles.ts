import styled from '@emotion/styled';

import { PortalProps } from '.';

export const Portal = styled.div<PortalProps>`
  position: fixed;
  z-index: ${({ theme }) => theme.zIndex.modal};
  top: 0;
  left: 0;

  display: block;

  width: 100%;
  height: 100%;

  pointer-events: ${({ disableScroll }) => (disableScroll ? 'auto' : 'none')};
`;
