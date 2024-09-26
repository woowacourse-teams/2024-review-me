import styled from '@emotion/styled';

export const AlertModalContainer = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
  justify-content: space-between;

  min-width: 30rem;
  max-width: 80vw;
  max-height: 80vh;
  padding: 3.2rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const Contents = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  white-space: pre-line;
`;
