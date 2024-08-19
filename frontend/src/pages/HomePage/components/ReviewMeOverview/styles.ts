import styled from '@emotion/styled';

export const ReviewMeOverview = styled.section`
  width: 65%;
  height: 100%;

  background-color: ${({ theme }) => theme.colors.lightPurple};

  padding: 5rem 10rem;
`;

export const RowSectionContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 5rem;
`;

export const ColumnSectionContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ArrowWrapper = styled.img`
  margin-top: 0.8rem;
`;

export const OverviewTitleContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1.3rem;

  margin-bottom: 3rem;
`;

export const OverviewTitle = styled.p`
  font-size: 3.8rem;
  font-weight: ${({ theme }) => theme.fontWeight.bold};

  margin-top: 1.3rem;
`;
