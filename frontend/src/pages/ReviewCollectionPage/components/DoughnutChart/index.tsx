import { useEffect, useState } from 'react';

import theme from '@/styles/theme';
import { ReviewVotes } from '@/types';

import generateGradientColors from '../../utils/generateGradientColors';
import DoughnutChartDetails from '../DoughnutChartDetails';

import * as S from './styles';

const DOUGHNUT_COLOR = {
  START: `${theme.colors.primary}`,
  END: '#e7e3f9',
};

const DoughnutChart = ({ reviewVotes }: { reviewVotes: ReviewVotes[] }) => {
  const radius = 90; // 차트의 반지름
  const circumference = 2 * Math.PI * radius; // 차트의 둘레
  const centerX = 125; // svg의 중앙 좌표 (x)
  const centerY = 125; // svg의 중앙 좌표 (y)

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

  // 애니메이션 상태 관리
  const [animateIndex, setAnimateIndex] = useState(0);

  // 애니메이션 트리거 설정
  useEffect(() => {
    if (animateIndex < reviewVotes.length - 1) {
      const timer = setTimeout(() => {
        setAnimateIndex(animateIndex + 1); // 다음 애니메이션 트리거
      }, 40);
      return () => clearTimeout(timer);
    }
  }, [animateIndex, reviewVotes.length]);

  // 각 조각의 중심 좌표를 계산하는 함수
  const calculateLabelPosition = (startAngle: number, endAngle: number) => {
    const midAngle = (startAngle + endAngle) / 2; // 중간 각도
    const labelRadius = radius * 1; // 텍스트가 배치될 반지름 (차트 내부)
    const x = centerX + labelRadius * Math.cos((midAngle * Math.PI) / 180);
    const y = centerY + labelRadius * Math.sin((midAngle * Math.PI) / 180);
    return { x, y };
  };

  return (
    <S.DoughnutChartContainer>
      <svg viewBox="0 0 250 250" width="250" height="250">
        {reviewVotes.map((reviewVote, index) => {
          const ratio = reviewVote.count / total;
          const fillSpace = circumference * ratio;
          const emptySpace = circumference - fillSpace;
          const offset = (acc[index] / total) * circumference;

          // 시작 각도와 끝 각도를 계산
          const startAngle = (acc[index] / total) * 360 + 90;
          const endAngle = ((acc[index] + reviewVote.count) / total) * 360 - 90;

          // 비율 레이블의 위치를 계산
          const { x, y } = calculateLabelPosition(startAngle, endAngle);

          return (
            <g key={index}>
              <circle
                cx={centerX} // 중앙에 배치
                cy={centerY}
                r={radius}
                fill="none"
                stroke={colors[index]}
                strokeWidth="65"
                strokeDasharray={`${fillSpace} ${emptySpace}`} // 조각의 길이와 나머지 길이 설정
                strokeDashoffset={-offset} // 시작 위치 설정
                style={{
                  transition: 'stroke-dasharray 1s ease', // 애니메이션 추가
                  opacity: index <= animateIndex ? 1 : 0, // 해당 인덱스까지의 애니메이션만 보여줌
                }}
              />
              <text
                x={x}
                y={y}
                textAnchor="middle"
                dominantBaseline="middle"
                fontSize="14"
                opacity={index <= animateIndex ? 1 : 0} // 애니메이션과 함께 텍스트 보이기
                style={{
                  transition: 'opacity 0.4s ease', // 텍스트의 투명도 애니메이션
                }}
              >
                {Math.floor(ratios[index] * 100)}%
              </text>
            </g>
          );
        })}
      </svg>
      <DoughnutChartDetails reviewVotes={reviewVotes} colors={colors} />
    </S.DoughnutChartContainer>
  );
};

export default DoughnutChart;
