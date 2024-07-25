import styled from '@emotion/styled';

export const ModalPortal = styled.div`
  position: fixed;
  z-index: ${({ theme }) => theme.zIndex.modal};
  top: 0;
  left: 0;

  display: block;

  width: 100%;
  height: 100%;
`;
