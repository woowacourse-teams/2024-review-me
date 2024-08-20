import styled from '@emotion/styled';

export const MainContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  min-height: calc(
    100vh - ${({ theme }) => theme.componentHeight.topbar} - ${({ theme }) => theme.componentHeight.footer}
  );
  margin-bottom: ${({ theme }) => theme.componentHeight.footer};
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  box-sizing: border-box;
  width: 100%;
  max-width: ${({ theme }) => theme.breakpoints.desktop};
  height: 100%;
  min-height: inherit;

  border-radius: 0.5rem;
`;
