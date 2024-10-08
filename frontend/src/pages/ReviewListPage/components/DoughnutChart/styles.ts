import styled from '@emotion/styled';

export const DoughnutChartContianer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  gap: 10rem;
`;

export const CircleSegment = styled.circle`
  animation: circle-fill-animation 2s ease;

  @keyframes circle-fill-animation {
    0% {
      stroke-dasharray: 0 ${2 * Math.PI * 90};
    }
  }
`;
