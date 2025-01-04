import styled from '@emotion/styled';

export const ReviewKeyword = styled.li`
  padding: 0.5rem 2rem;

  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.primary};

  background-color: ${({ theme }) => theme.colors.lightPurple};
  border-radius: 1.4rem;
`;
