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

  list-style-type: none;

  padding: 0 2.5rem;

  gap: 3rem;

  ${media.xSmall} {
    width: 100%;
    padding: 0;
    gap: 0;
  }
`;

export const NavItem = styled.li<{ selected: boolean }>`
  padding: 0.7rem 1rem;
  margin-bottom: 1rem;
  border-radius: 0.5rem;

  button {
    color: ${({ theme, selected }) => (selected ? theme.colors.black : theme.colors.disabled)};
    font-weight: ${({ theme }) => theme.fontWeight.semibold};

    &:hover {
      color: ${({ theme }) => theme.colors.black};
    }
  }

  ${media.xSmall} {
    display: flex;
    justify-content: center;

    flex: 1;
    padding: 0.7rem 0;
  }
`;

export const CurrentNavBar = styled.div<{ width: number; left: number; isTransitionEnabled: boolean }>`
  position: absolute;
  left: ${({ left }) => `${left}px`};
  bottom: 0;

  width: ${({ width }) => `${width}px`};
  height: 0.3rem;

  background-color: ${({ theme }) => theme.colors.primary};
  border-radius: 0.1rem;

  transition: ${({ isTransitionEnabled }) => (isTransitionEnabled ? 'all 0.2s ease-in-out' : 'none')};
`;
