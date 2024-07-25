import styled from '@emotion/styled';

export const SidebarBackground = styled.div`
  width: 100%;
  height: 100%;
  background-color: ${({ theme }) => theme.colors.sidebarBackground};
`;

interface SidebarWrapperProps {
  $isSidebarHidden: boolean;
}

export const SidebarWrapper = styled.div<SidebarWrapperProps>`
  position: absolute;
  top: 0;
  left: ${(props) => (props.$isSidebarHidden ? '-100%' : 0)};
  transition: left 1s ease-in-out;
`;
