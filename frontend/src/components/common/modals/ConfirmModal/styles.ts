import styled from '@emotion/styled';

export const ConfirmModalContainer = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  gap: 3.2rem;
  justify-content: space-between;

  width: fit-content;
  max-width: 40vh;
  max-height: 80vh;
  padding: 3.2rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const Contents = styled.div`
  display: flex;
  align-items: center;
  white-space: pre-line;
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
