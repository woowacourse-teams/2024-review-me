import styled from '@emotion/styled';

export const ReviewMeOverview = styled.section`
  position: relative;
  overflow: hidden;
  width: 65%;
  background-color: ${({ theme }) => theme.colors.lightPurple};
`;

export const ColumnSectionContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  height: 100%;
  padding: 3rem;
`;

export const ArrowWrapper = styled.img`
  margin-top: 0.8rem;
`;

export const OverviewTitleContainer = styled.div`
  display: flex;
  gap: 1.3rem;
  align-items: center;
  justify-content: center;

  margin-bottom: 2rem;

  img {
    width: 7rem;
    height: 7rem;
  }
`;

export const OverviewTitle = styled.p`
  margin-top: 1.3rem;
  font-size: ${({ theme }) => theme.fontSize.large};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`;
