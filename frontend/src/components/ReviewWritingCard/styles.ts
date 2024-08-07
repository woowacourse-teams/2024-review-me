import styled from '@emotion/styled';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Title = styled.span`
  margin-bottom: 2rem;
  font-size: ${({ theme }) => theme.fontSize.medium};
`;
