import styled from '@emotion/styled';

import media from '@/utils/media';

export const NavContainer = styled.nav`
  position: relative;
  display: flex;
  width: calc(100vw - ${({ theme }) => theme.scrollbarWidth.basic});
  height: 4rem;

  border-bottom: 0.1rem solid ${({ theme }) => theme.colors.lightGray};

  ${media.small} {
    width: calc(100vw - ${({ theme }) => theme.scrollbarWidth.small});
  }
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
