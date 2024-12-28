import styled from '@emotion/styled';

export const NavContainer = styled.nav`
  position: relative;

  display: flex;
  width: 100vw;

  border-bottom: 0.1rem solid ${({ theme }) => theme.colors.lightGray};
`;

export const NavList = styled.ul`
  display: flex;

  list-style-type: none;

  padding: 0 2.5rem;

  gap: 4rem;
`;

export const NavItem = styled.li<{ selected: boolean }>`
  padding: 1.2rem 0;

  button {
    color: ${({ theme, selected }) => (selected ? theme.colors.primary : theme.colors.disabled)};
    font-weight: ${({ theme }) => theme.fontWeight.semibold};
  }
`;

export const CurrentNavBar = styled.div<{ width: number; left: number }>`
  position: absolute;
  left: ${({ left }) => `${left}px`};
  bottom: 0;

  width: ${({ width }) => `${width}px`};
  height: 0.3rem;

  background-color: ${({ theme }) => theme.colors.primary};
  border-radius: 0.1rem;

  transition: all 0.2s ease-in-out;
`;
