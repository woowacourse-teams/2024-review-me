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
  justify-content: space-between;
  align-items: center;

  gap: 1rem;
`;

export const ContentContainer = styled.div`
  display: flex;
  align-items: center;

  gap: 1rem;
`;

export const ChartColor = styled.div<{ color: string }>`
  background-color: ${({ color }) => color};

  width: 2rem;
  height: 2rem;

  border-radius: 0.5rem;
  flex-shrink: 0;

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
