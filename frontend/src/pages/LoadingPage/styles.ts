import styled from '@emotion/styled';

export const Container = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  align-items: center;
  justify-content: center;

  width: 100%;
`;

export const Text = styled.span`
  font-size: ${({ theme }) => theme.fontSize.basic};
  color: ${({ theme }) => theme.colors.primary};
`;
