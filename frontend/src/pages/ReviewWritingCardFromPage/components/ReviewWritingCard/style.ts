import styled from '@emotion/styled';

export const ReviewWritingCard = styled.div`
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
  padding: 2rem;
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: flex-end;
  padding-right: 2.5rem;

  button {
    width: auto;
    min-width: 8rem;
    height: 3.5rem;
  }
`;
