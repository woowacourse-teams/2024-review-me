import { Option } from '../DoughnutChart';

import * as S from './styles';

interface DoughnutChartDetails {
  options: Option[];
  ratios: number[];
}

const DoughnutChartDetails = ({ options, ratios }: DoughnutChartDetails) => {
  return (
    <S.DoughnutChartDetailList>
      {options.map((option, index) => (
        <S.DetailItem key={option.label}>
          <S.ChartColor color={option.color}></S.ChartColor>
          <S.Description>{option.label}</S.Description>
          <span>{ratios[index] * 100}%</span>
        </S.DetailItem>
      ))}
    </S.DoughnutChartDetailList>
  );
};

export default DoughnutChartDetails;
