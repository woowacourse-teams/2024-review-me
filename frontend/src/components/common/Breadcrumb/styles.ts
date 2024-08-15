import styled from '@emotion/styled';

export const BreadcrumbList = styled.ul`
  display: flex;
  list-style: none;

  position: absolute;
  left: 2.5rem;
  top: 8rem;
`;

export const BreadcrumbItem = styled.li`
  cursor: pointer;

  &:not(:last-child)::after {
    content: '/';
    margin: 0 2.5rem;
  }

  &:last-child {
    color: ${({ theme }) => theme.colors.primary};
    text-decoration: underline;
  }
`;
