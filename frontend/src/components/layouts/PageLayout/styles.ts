import styled from '@emotion/styled';

export const Layout = styled.div`
  width: 100vw;
  background-color: ${({ theme }) => theme.colors.sidebarBackground};
`;

export const Wrapper = styled.div`
  position: relative;

  overflow-x: hidden;
  display: flex;
  flex-direction: column;

  width: inherit;

  background-color: ${({ theme }) => theme.colors.white};
`;
