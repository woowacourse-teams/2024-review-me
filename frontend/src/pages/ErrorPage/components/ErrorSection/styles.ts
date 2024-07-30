import styled from '@emotion/styled';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3rem;
  align-items: center;
  justify-content: center;

  height: calc(100vh - 21rem);
`;

export const ErrorLogoWrapper = styled.div`
  & > img {
    width: 15rem;
    height: 15rem;
  }
`;

export const ErrorMessage = styled.span`
  font-size: 2rem;
`;

export const Container = styled.div`
  display: flex;
  gap: 3.5rem;
  align-items: center;
  justify-content: center;
`;

export const ButtonContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 3rem;

  & > button {
    width: 17rem;
    height: 5rem;
    font-size: 1.4rem;
  }
`;
