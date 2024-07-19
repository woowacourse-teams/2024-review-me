import styled from '@emotion/styled';

export const MainContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 6rem;
  padding: 0 3rem;
`;

export const Contents = styled.div`
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
  border-radius: 0.5rem;
  max-width: ${({ theme }) => theme.breakpoints.desktop};
`;
