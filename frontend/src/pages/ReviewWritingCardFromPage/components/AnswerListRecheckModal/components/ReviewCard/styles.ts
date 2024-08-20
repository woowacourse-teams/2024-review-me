import styled from '@emotion/styled';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Header = styled.div`
  width: 100%;
  height: 5rem;
  padding: 1rem 2rem;
  background-color: ${({ theme }) => theme.colors.lightPurple};
`;

export const Main = styled.div`
  padding: 2rem;
`;

export const Title = styled.span`
  margin-bottom: 2rem;
  font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
`;
