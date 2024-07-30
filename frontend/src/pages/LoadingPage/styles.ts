import styled from '@emotion/styled';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: calc(100vh - 21rem);
`;

export const Text = styled.span`
  font-size: ${({ theme }) => theme.fontSize.basic};
  color: ${({ theme }) => theme.colors.primary};
`;
