import { ReviewVotes } from '@/types';

import * as S from './styles';

interface DoughnutChartDetails {
  reviewVotes: ReviewVotes[];
  ratios: number[];
  colors: string[];
}

const DoughnutChartDetails = ({ reviewVotes, ratios, colors }: DoughnutChartDetails) => {
  return (
    <S.DoughnutChartDetailList>
      {reviewVotes.map((reviewVote, index) => (
        <S.DetailItem key={reviewVote.content}>
          <S.ContentContainer>
            <S.ChartColor color={colors[index]}></S.ChartColor>
            <S.Description>{reviewVote.content}</S.Description>
          </S.ContentContainer>
          <span>{Math.floor(ratios[index] * 100)}%</span>
        </S.DetailItem>
      ))}
    </S.DoughnutChartDetailList>
  );
};

export default DoughnutChartDetails;
