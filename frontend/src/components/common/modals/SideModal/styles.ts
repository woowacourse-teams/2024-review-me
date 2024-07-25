import styled from '@emotion/styled';

interface SidebarWrapperProps {
  $isSidebarHidden: boolean;
}

export const SidebarWrapper = styled.div<SidebarWrapperProps>`
  position: absolute;
  top: 0;
  left: ${(props) => (props.$isSidebarHidden ? '-100%' : 0)};
  transition: left 1s ease-in-out;
`;
