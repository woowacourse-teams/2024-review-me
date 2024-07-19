import styled from '@emotion/styled';

interface SidebarProps {
  $isOpen: boolean;
}
export const Sidebar = styled.div<SidebarProps>`
  width: ${({ theme }) => theme.sidebarWidth.desktop};
  height: 100vh;
  position: fixed;
  left: 0;

  display: flex;
  flex-direction: column;
  padding: 1rem 1rem 0.7rem 1rem;
  transform: translateX(${(props) => (props.$isOpen ? 0 : '-100%')});

  background-color: #ffffff;
  border-radius: 0 1rem 1rem 0;
  transition: transform 1s ease-in-out;
  z-index: ${({ theme }) => theme.zIndex.sidebar};
  filter: drop-shadow(0.25rem 0.25rem 0.25rem lightgrey);

  @media screen and (max-width: ${({ theme }) => theme.breakpoints.mobile}) {
    width: ${({ theme }) => theme.sidebarWidth.mobile};
  }
`;

export const Top = styled.div`
  width: 100%;
  height: 3rem;

  display: flex;
  justify-content: space-between;
  align-items: center;

  margin-bottom: 5rem;
`;

export const MenuList = styled.ul`
  display: flex;
  flex-direction: column;
`;

export const MenuItem = styled.li<{ selected: boolean }>`
  display: flex;
  align-items: center;
  height: 3.5rem;

  padding-left: 1rem;
  font-size: 1.2rem;
  font-weight: 700;
  cursor: pointer;

  border-left: ${({ selected }) => (selected ? '2px solid #7361df' : 'none')};
  color: ${({ selected }) => (selected ? '#7361df' : 'none')};

  &:hover {
    border-left: 2px solid #7361df;
    color: #7361df;
    font-weight: 800;
  }

  a:visited,
  a:active,
  a:-webkit-any-link {
    text-decoration-line: none;
  }
`;

export const LogoIcon = styled.img`
  width: 3.5rem;
  height: 3.5rem;
`;

export const CloseIcon = styled.img`
  width: 1.3rem;
  height: 1.3rem;
`;
