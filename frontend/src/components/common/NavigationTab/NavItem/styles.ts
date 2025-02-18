import styled from '@emotion/styled';

import media from '@/utils/media';

interface NavItemProps {
  $isActiveTab: boolean;
}

export const NavItem = styled.li<NavItemProps>`
  border-bottom: 0.3rem solid ${({ theme, $isActiveTab }) => ($isActiveTab ? theme.colors.primary : 'none')};
  padding: 0 1rem 1.3rem 1rem;

  button {
    font-weight: ${({ theme }) => theme.fontWeight.semibold};
    color: ${({ theme, $isActiveTab }) => ($isActiveTab ? theme.colors.black : theme.colors.disabled)};

    &:hover {
      color: ${({ theme }) => theme.colors.black};
    }
  }

  ${media.xSmall} {
    display: flex;
    flex: 1;
    justify-content: center;

    margin: 0 2rem;
  }

  ${media.xxSmall} {
    margin: 0 1.6rem;
  }
`;
