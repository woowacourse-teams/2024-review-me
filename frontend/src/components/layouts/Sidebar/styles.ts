import styled from '@emotion/styled';

export const Sidebar = styled.div`
  position: fixed;

  display: flex;
  flex-direction: column;

  width: ${({ theme }) => theme.sidebarWidth.desktop};
  height: 100vh;
  padding: 1rem 1rem 0.7rem;

  background-color: #fff;
  filter: drop-shadow(0.25rem 0.25rem 0.25rem lightgrey);
  border-radius: 0 1rem 1rem 0;

  @media screen and (max-width: ${({ theme }) => theme.breakpoints.mobile}) {
    width: ${({ theme }) => theme.sidebarWidth.mobile};
  }
`;

export const Top = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 3rem;
  margin-bottom: 5rem;
`;

export const MenuList = styled.ul`
  display: flex;
  flex-direction: column;
`;

export const MenuItem = styled.li<{ selected: boolean }>`
  cursor: pointer;

  display: flex;
  align-items: center;

  height: 3.5rem;
  padding-left: 1rem;

  font-size: 1.2rem;
  font-weight: 700;
  color: ${({ selected }) => (selected ? '#7361df' : 'none')};

  border-left: ${({ selected }) => (selected ? '2px solid #7361df' : 'none')};

  &:hover {
    font-weight: 800;
    color: #7361df;
    border-left: 2px solid #7361df;
  }

  a:visited,
  a:active,
  a:any-link {
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
