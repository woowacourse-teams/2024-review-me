import styled from '@emotion/styled';

export const ErrorAlertModalContainer = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  gap: 3.2rem;
  align-items: center;
  justify-content: space-between;

  max-width: 40vw;
  max-height: 80vh;
  padding: 3.2rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const AlertTriangle = styled.img`
  width: 6rem;
  height: 6rem;
`;

export const AlertMessage = styled.p``;
