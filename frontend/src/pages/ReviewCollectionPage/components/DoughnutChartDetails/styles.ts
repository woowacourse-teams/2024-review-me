import styled from '@emotion/styled';

import media from '@/utils/media';

export const DoughnutChartDetailList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  margin: 2rem;

  ${media.small} {
    margin: 0 1rem;
  }
`;

export const DetailItem = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;
  justify-content: space-between;
`;

export const ContentContainer = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;
`;

export const ChartColor = styled.div<{ color: string }>`
  flex-shrink: 0;

  width: 2rem;
  height: 2rem;

  background-color: ${({ color }) => color};
  border-radius: 0.5rem;

  ${media.small} {
    width: 1.6rem;
    height: 1.6rem;
  }
`;

export const Description = styled.span`
  ${media.small} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;

export const ReviewVoteResult = styled.span`
  ${media.small} {
    font-size: ${({ theme }) => theme.fontSize.small};
  }
`;
