import styled from '@emotion/styled';

export const Layout = styled.div`
  width: 100%;
`;

export const Wrapper = styled.div`
  position: relative;

  overflow-x: hidden;
  display: flex;
  flex-direction: column;

  width: 100%;
  min-height: 100vh;

  background-color: ${({ theme }) => theme.colors.white};
`;
