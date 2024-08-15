import styled from '@emotion/styled';

export const BreadcrumbList = styled.ul`
  position: absolute;
  top: 8rem;
  left: 2.5rem;

  display: flex;

  list-style: none;
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
