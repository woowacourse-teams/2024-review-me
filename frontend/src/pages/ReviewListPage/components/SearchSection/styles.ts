import styled from '@emotion/styled';

export const Container = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 4rem;

  button {
    width: 8rem;
    color: ${({ theme }) => theme.colors.primary};
  }
`;

export const SearchBox = styled.div`
  display: flex;
  gap: 2rem;
`;
