import styled from '@emotion/styled';

export const BreadcrumbList = styled.ul`
  display: flex;
  padding: 2rem 0 0 2.5rem;
  list-style: none;
`;

export const BreadcrumbItem = styled.li`
  cursor: pointer;

  &:not(:last-child)::after {
    content: '/';
    margin: 0 2rem;
  }

  &:last-child {
    color: ${({ theme }) => theme.colors.primary};
    text-decoration: underline;
  }
`;
