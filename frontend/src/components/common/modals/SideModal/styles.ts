import styled from '@emotion/styled';

interface SidebarWrapperProps {
  $isSidebarHidden: boolean;
}

export const SidebarWrapper = styled.div<SidebarWrapperProps>`
  position: absolute;
  top: 0;
  left: ${(props) => (props.$isSidebarHidden ? '-100%' : 0)};
  transition: left 0.2s ease-in-out;
`;
