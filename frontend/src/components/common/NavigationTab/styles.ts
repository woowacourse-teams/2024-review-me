import styled from '@emotion/styled';

import media from '@/utils/media';

export const NavContainer = styled.nav`
  position: relative;
  display: flex;
  width: 100vw;
  border-bottom: 0.1rem solid ${({ theme }) => theme.colors.lightGray};
`;

export const NavList = styled.ul`
  display: flex;
  gap: 3rem;
  padding: 0 2.5rem;
  list-style-type: none;

  ${media.xSmall} {
    gap: 0;
    width: 100%;
    padding: 0;
  }
`;

export const NavItem = styled.li<{ selected: boolean }>`
  margin-bottom: 1rem;
  padding: 0.7rem 1rem;
  border-radius: 0.5rem;

  button {
    font-weight: ${({ theme }) => theme.fontWeight.semibold};
    color: ${({ theme, selected }) => (selected ? theme.colors.black : theme.colors.disabled)};

    &:hover {
      color: ${({ theme }) => theme.colors.black};
    }
  }

  ${media.xSmall} {
    display: flex;
    flex: 1;
    justify-content: center;
    padding: 0.7rem 0;
  }
`;

export const CurrentNavBar = styled.div<{ width: number; left: number; isTransitionEnabled: boolean }>`
  position: absolute;
  bottom: 0;
  left: ${({ left }) => `${left}px`};

  width: ${({ width }) => `${width}px`};
  height: 0.3rem;

  background-color: ${({ theme }) => theme.colors.primary};
  border-radius: 0.1rem;

  transition: ${({ isTransitionEnabled }) => (isTransitionEnabled ? 'all 0.2s ease-in-out' : 'none')};
`;
