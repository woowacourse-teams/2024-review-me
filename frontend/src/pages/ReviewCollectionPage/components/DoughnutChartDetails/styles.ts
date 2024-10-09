import styled from '@emotion/styled';

export const DoughnutChartDetailList = styled.div`
  display: flex;
  flex-direction: column;

  gap: 2rem;

  margin: 2rem;
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
`;

export const Description = styled.span``;
