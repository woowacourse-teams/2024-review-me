import styled from '@emotion/styled';

export const BreadcrumbList = styled.ul`
  display: flex;

  max-height: ${({ theme }) => theme.componentHeight.breadCrumb};
  padding: ${({ theme }) => theme.breadcrumbSize.paddingLeft} 0 0 2.5rem;

  font-size: 1.5rem;
  list-style: none;
`;

export const BreadcrumbItem = styled.li`
  cursor: pointer;
  display: flex;

  &:not(:last-child)::after {
    content: '/';
    margin: 0 2rem;
  }

  &:last-child {
    color: ${({ theme }) => theme.colors.primary};
    text-decoration: underline;
  }
`;
