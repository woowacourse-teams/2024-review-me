import styled from '@emotion/styled';

export const Layout = styled.div`
  width: 100vw;
  background-color: ${({ theme }) => theme.colors.sidebarBackground};
`;

export const Wrapper = styled.div`
  width: inherit;
  //margin: 0 auto;
  position: relative;
  background-color: ${({ theme }) => theme.colors.white};
  //max-width: ${({ theme }) => theme.breakpoints.desktop};
  display: flex;
  flex-direction: column;
  overflow-x: hidden;

  /* @media screen and (min-width: ${({ theme }) => theme.breakpoints.desktop}) {
    width: ${({ theme }) => theme.breakpoints.desktop};
    margin: 0 auto;
  } */
`;
