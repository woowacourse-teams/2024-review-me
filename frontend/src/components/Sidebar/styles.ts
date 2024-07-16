import styled from '@emotion/styled';

export const Sidebar = styled.div`
  width: 18rem;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #f4faff;
  border-radius: 0 1rem 1rem 0;
  position: fixed;
  left: 0;
  z-index: 999;
  padding-bottom: 10rem;

  filter: drop-shadow(0.25rem 0.25rem 0.25rem lightgrey);
`;

export const Top = styled.div`
  width: 100%;
  height: 3rem;

  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1rem;

  & > button {
    width: 2rem;
    height: 2rem;

    img {
      width: 100%;
      max-width: initial;
    }
  }
`;

export const UserInfo = styled.div`
  display: flex;
  flex-direction: column;

  align-items: center;
  gap: 1rem;
`;

export const ProfileImage = styled.div`
  width: 7rem;
  height: 7rem;

  border-radius: 100%;

  --s: 20px; /* size of the lines */
  --g: 15px; /* gap between lines */

  --c1: #c02942;
  --c2: #53777a;

  background:
    conic-gradient(at var(--s) calc(100% - var(--s)), #0000 270deg, var(--c1) 0) calc(var(--s) + var(--g)) 0,
    linear-gradient(var(--c2) var(--s), #0000 0) 0 var(--g),
    conic-gradient(at var(--s) calc(100% - var(--s)), #0000 90deg, var(--c2) 0 180deg, var(--c1) 0),
    #ecd078;
  background-size: calc(2 * (var(--s) + var(--g))) calc(2 * (var(--s) + var(--g)));
`;

export const ProfileId = styled.div`
  font-size: 1.5rem;
  font-weight: 600;
`;

export const MenuList = styled.ul`
  display: flex;
  flex-direction: column;
`;

export const MenuItem = styled.li<{ selected: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 3.5rem;
  border-radius: 1rem 0 0 1rem;
  margin-left: 1rem;
  padding-right: 1rem;
  font-size: 1rem;
  font-weight: 700;
  cursor: pointer;
  background-color: ${({ selected }) => (selected ? '#a2daff' : '#f4faff')};
  &:hover {
    background-color: ${({ selected }) => (selected ? '#a2daff' : '#e1f3ff')};
  }
  a:visited,
  a:active,
  a:-webkit-any-link {
    text-decoration-line: none;
  }
`;
