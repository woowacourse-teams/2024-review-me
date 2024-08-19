import styled from '@emotion/styled';

export const Footer = styled.footer`
  position: absolute;
  bottom: 0;
  left: 0;

  display: flex;
  gap: 3.2rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: ${({ theme }) => theme.footerHeight};
  padding: 1rem;

  font-size: ${({ theme }) => theme.fontSize.small};
  color: ${({ theme }) => theme.colors.gray};

  background-color: ${({ theme }) => theme.colors.white};
`;

export const Link = styled.a`
  &,
  &:visited,
  &:hover,
  &:focus,
  &:active {
    color: inherit;
    text-decoration: none;
  }
`;
