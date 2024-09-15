import styled from '@emotion/styled';

export const ReviewWritingCard = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Header = styled.div`
  width: 100%;
  padding: 1rem 2rem;

  font-size: ${({ theme }) => theme.fontSize.mediumSmall};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};

  background-color: ${({ theme }) => theme.colors.lightPurple};

  @media screen and (max-width: 768px) {
    font-size: ${({ theme }) => theme.fontSize.basic};
  }
`;

export const Main = styled.div`
  padding: 2rem 2rem 1rem 2rem;
`;
