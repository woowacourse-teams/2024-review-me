import styled from '@emotion/styled';

export const ContentModalContainer = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  overflow: hidden;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;

  min-width: 30rem;
  max-width: 80vw;
  max-height: 90vh;
  padding: 3.2rem;

  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.basic};
`;

export const ContentModalHeader = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 100%;
  height: 3rem;
`;

export const Contents = styled.div`
  overflow-y: auto;
  padding-right: 1.2rem;
`;

export const CloseButton = styled.button`
  width: 2.4rem;
  height: 2.4rem;
`;
