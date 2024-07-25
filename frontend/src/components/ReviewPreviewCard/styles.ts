import styled from '@emotion/styled';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;

  width: 100%;

  border: 1px solid ${({ theme }) => theme.colors.lightGray};
  border-radius: 8px;

  &:hover {
    cursor: pointer;
    border: 1px solid ${({ theme }) => theme.colors.primaryHover};

    & > div:first-of-type {
      background-color: ${({ theme }) => theme.colors.primaryHover};
    }
  }
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;

  height: 6rem;
  padding: 1rem 3rem;

  background-color: ${({ theme }) => theme.colors.lightGray};
  border-top-left-radius: 0.8rem;
  border-top-right-radius: 0.8rem;
`;

export const HeaderContainer = styled.div`
  display: flex;
  gap: 1rem;

  img {
    width: 4rem;
  }
`;

export const Title = styled.div`
  font-size: 1.6rem;
  font-weight: 700;
`;

export const SubTitle = styled.div`
  font-size: 1.2rem;
`;

export const Visibility = styled.div`
  display: flex;
  gap: 0.6rem;
  align-items: center;
  font-size: 1.6rem;
  font-weight: 700;

  img {
    width: 2rem;
  }
`;

export const Main = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  padding: 2rem 3rem;

  font-size: 1.6rem;
`;

export const Keyword = styled.div`
  display: flex;
  gap: 3rem;
  align-items: center;
  font-size: 1.4rem;

  div {
    padding: 0.5rem 3rem;
    background-color: ${({ theme }) => theme.colors.primaryHover};
    border-radius: 0.8rem;
  }
`;
