import styled from '@emotion/styled';

export const ConfirmModalInnerWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;
`;

export const ConfirmModalInner = styled.div`
  display: flex;
  flex-direction: column;
  gap: 3.2rem;
  justify-content: space-between;

  padding: 3.2rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const Contents = styled.div`
  display: flex;
  align-items: center;

  min-width: 25rem;
  min-height: 10rem;
  max-height: 40vh;
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 10%;
  align-items: center;
  justify-content: space-between;
  button {
    width: 40%;
    max-width: 20rem;
  }
`;
