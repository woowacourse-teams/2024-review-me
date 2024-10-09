import { ReviewVotes } from '@/types';

import generateGradientColors from '../../utils/generateGradientColors';
import DoughnutChartDetails from '../DoughnutChartDetails';

import * as S from './styles';

const DOUGHNUT_COLOR = {
  START: '#7361DF',
  END: '#e7e3f9',
};

const DoughnutChart = ({ reviewVotes }: { reviewVotes: ReviewVotes[] }) => {
  const radius = 90; // 차트의 반지름
  const circumference = 2 * Math.PI * radius; // 차트의 둘레

  const total = reviewVotes.reduce((acc, reviewVote) => acc + reviewVote.count, 0);
  const ratios = reviewVotes.map((reviewVote) => reviewVote.count / total);

  // 누적 값 계산
  const acc = reviewVotes.reduce(
    (arr, reviewVote) => {
      const last = arr[arr.length - 1];
      return [...arr, last + reviewVote.count]; // 현재 값과 이전 누적 값을 더해 새로운 배열 반환
    },
    [0],
  );

  // 색상 시작 및 끝값 정의
  const colors = generateGradientColors(reviewVotes.length, DOUGHNUT_COLOR.START, DOUGHNUT_COLOR.END);

  return (
    <S.DoughnutChartContianer>
      <svg viewBox="0 0 250 250" width="250" height="250">
        {reviewVotes.map((reviewVote, index) => {
          const ratio = reviewVote.count / total;
          const fillSpace = circumference * ratio;
          const emptySpace = circumference - fillSpace;
          const offset = (acc[index] / total) * circumference;

          return (
            <circle
              key={index}
              cx="125" // 중앙에 배치
              cy="125"
              r={radius}
              fill="none"
              stroke={colors[index]}
              strokeWidth="65"
              strokeDasharray={`${fillSpace} ${emptySpace}`} // 조각의 길이와 나머지 길이 설정
              strokeDashoffset={-offset} // 시작 위치 설정
            />
          );
        })}
      </svg>
      <DoughnutChartDetails reviewVotes={reviewVotes} ratios={ratios} colors={colors} />
    </S.DoughnutChartContianer>
  );
};

export default DoughnutChart;
