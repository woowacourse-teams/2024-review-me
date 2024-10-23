import theme from '@/styles/theme';
import { ReviewVotes } from '@/types';

import generateGradientColors from '../../utils/generateGradientColors';
import DoughnutChartDetails from '../DoughnutChartDetails';

import * as S from './styles';

const DOUGHNUT_COLOR = {
  start: `${theme.colors.primary}`,
  end: '#e7e3f9',
};

const CHART_RADIUS = 90;
const SVG_VIEWBOX = '0 0 250 250';
const SVG_SIZE = 250;
const STROKE_WIDTH = 65;

const DoughnutChart = ({ reviewVotes }: { reviewVotes: ReviewVotes[] }) => {
  const circumference = 2 * Math.PI * CHART_RADIUS; // 차트의 둘레
  const centerX = SVG_SIZE / 2; // svg의 중앙 x 좌표
  const centerY = SVG_SIZE / 2; // svg의 중앙 y 좌표

  const nonZeroReviewVotes = reviewVotes.filter((reviewVote) => reviewVote.count > 0);

  const totalReviewCount = nonZeroReviewVotes.reduce((acc, reviewVote) => acc + reviewVote.count, 0);
  const reviewVoteRatios = nonZeroReviewVotes.map((reviewVote) => reviewVote.count / totalReviewCount);

  // 누적 값 계산
  const cumulativeVotes = nonZeroReviewVotes.reduce(
    (arr, reviewVote) => {
      arr.push(arr[arr.length - 1] + reviewVote.count);
      return arr;
    },
    [0],
  );

  // 색상 시작 및 끝값 정의
  const chartColors = generateGradientColors({
    length: reviewVotes.length,
    startHex: DOUGHNUT_COLOR.start,
    endHex: DOUGHNUT_COLOR.end,
  });

  // 각 조각의 중심 좌표를 계산하는 함수
  const calculateLabelPosition = (startAngle: number, endAngle: number) => {
    const midAngle = (startAngle + endAngle) / 2; // 중간 각도
    const labelRadius = CHART_RADIUS * 1; // 텍스트가 배치될 반지름 (차트 내부)
    const x = centerX + labelRadius * Math.cos((midAngle * Math.PI) / 180);
    const y = centerY + labelRadius * Math.sin((midAngle * Math.PI) / 180);
    return { x, y };
  };

  return (
    <S.DoughnutChartContainer>
      <svg viewBox={SVG_VIEWBOX} width={SVG_SIZE} height={SVG_SIZE}>
        {nonZeroReviewVotes.map((reviewVote, index) => {
          const ratio = reviewVote.count / totalReviewCount;
          const fillSpace = circumference * ratio;
          const emptySpace = circumference - fillSpace;
          const offset = (cumulativeVotes[index] / totalReviewCount) * circumference;

          // 시작 각도와 끝 각도를 계산
          const startAngle = (cumulativeVotes[index] / totalReviewCount) * 360 + 90;
          const endAngle = ((cumulativeVotes[index] + reviewVote.count) / totalReviewCount) * 360 - 90;

          // 비율 레이블의 위치를 계산
          const { x, y } = calculateLabelPosition(startAngle, endAngle);

          return (
            <g key={index}>
              <circle
                cx={centerX} // 중앙에 배치
                cy={centerY}
                r={CHART_RADIUS}
                fill="none"
                stroke={chartColors[index]}
                strokeWidth={STROKE_WIDTH}
                strokeDasharray={`${fillSpace} ${emptySpace}`} // 조각의 길이와 나머지 길이 설정
                strokeDashoffset={-offset} // 시작 위치 설정
              />
              <text x={x} y={y} textAnchor="middle" dominantBaseline="middle" fontSize="14">
                {(reviewVoteRatios[index] * 100).toFixed(1)}%
              </text>
            </g>
          );
        })}
      </svg>
      <DoughnutChartDetails reviewVotes={reviewVotes} colors={chartColors} />
    </S.DoughnutChartContainer>
  );
};

export default DoughnutChart;
