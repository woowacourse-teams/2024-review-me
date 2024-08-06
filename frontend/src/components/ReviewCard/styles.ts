import styled from '@emotion/styled';

export const Layout = styled.div`
  display: flex;
  flex-direction: column;

  width: 100%;

  border: 0.1rem solid ${({ theme }) => theme.colors.lightGray};
  border-radius: 0.8rem;

  &:hover {
    cursor: pointer;
    border: 0.1rem solid ${({ theme }) => theme.colors.lightPurple};

    & > div:first-of-type {
      background-color: ${({ theme }) => theme.colors.lightPurple};
    }
  }
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;

  height: 6rem;
  padding: 1rem 3rem;

  background-color: ${({ theme }) => theme.colors.lightGray};
  border-radius: 0.8rem 0.8rem 0 0;
`;

export const HeaderContent = styled.div`
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
  flex-wrap: wrap;
  gap: 2.5rem;
  align-items: center;

  font-size: 1.4rem;

  div {
    padding: 0.5rem 3rem;
    background-color: ${({ theme }) => theme.colors.lightPurple};
    border-radius: 0.8rem;
  }
`;
