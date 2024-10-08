import DoughnutChartDetails from '../DoughnutChartDetails';

import * as S from './styles';

export interface Option {
  label: string;
  count: number;
  color: string;
}

const options: Option[] = [
  { label: '커뮤니케이션, 협업 능력', count: 3, color: '#7361DF' },
  { label: '문제 해결 능력', count: 5, color: '#9082e6' },
  { label: '시간 관리 능력', count: 2, color: '#ada2ec' },
  { label: '기술적 역량', count: 4, color: '#d8d3f6' },
  { label: '성장 마인드셋', count: 6, color: '#e7e3f9' },
];

const total = options.reduce((acc, option) => acc + option.count, 0);
const radius = 90; // 차트의 반지름
const circumference = 2 * Math.PI * radius; // 차트의 둘레

// 누적 값 계산
const acc = options.reduce(
  (arr, option) => {
    const last = arr[arr.length - 1];
    return [...arr, last + option.count]; // 현재 값과 이전 누적 값을 더해 새로운 배열 반환
  },
  [0],
);

/* 차트 애니메이션 */

/* 차트 애니메이션 끝 */

const DoughnutChart = () => {
  const ratios = options.map((option) => option.count / total);

  return (
    <S.DoughnutChartContianer>
      <svg viewBox="0 0 250 250" width="250" height="250">
        {options.map((option, index) => {
          const ratio = option.count / total;
          const fillSpace = circumference * ratio;
          const emptySpace = circumference - fillSpace;
          const offset = (acc[index] / total) * circumference;

          return (
            <g key={index}>
              <S.CircleSegment
                key={index}
                cx="125" // 중앙에 배치
                cy="125"
                r={radius}
                fill="none"
                stroke={option.color}
                strokeWidth="65"
                strokeDasharray={`${fillSpace} ${emptySpace}`} // 조각의 길이와 나머지 길이 설정
                strokeDashoffset={-offset} // 시작 위치 설정
              />
            </g>
          );
        })}
      </svg>
      <DoughnutChartDetails options={options} ratios={ratios} />
    </S.DoughnutChartContianer>
  );
};

export default DoughnutChart;
