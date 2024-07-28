import styled from '@emotion/styled';

export const Container = styled.select`
  width: 12rem;
  padding: 0.8rem;

  font-size: 1.6rem;

  border: 1px solid ${({ theme }) => theme.colors.placeholder};
  border-radius: 0.8rem;
`;
