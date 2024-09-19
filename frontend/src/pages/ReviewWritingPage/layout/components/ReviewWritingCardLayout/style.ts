import styled from '@emotion/styled';

export const ReviewWritingCardLayout = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Header = styled.div`
  width: 100%;
  min-height: 5rem;
  padding: 1rem 2rem;

  font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};

  background-color: ${({ theme }) => theme.colors.lightPurple};
`;

export const Main = styled.div`
  padding: 2rem 2rem 1rem 2rem;
`;
