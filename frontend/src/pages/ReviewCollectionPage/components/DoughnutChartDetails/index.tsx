import { ReviewVotes } from '@/types';

import * as S from './styles';

interface DoughnutChartDetails {
  reviewVotes: ReviewVotes[];
  colors: string[];
}

const DoughnutChartDetails = ({ reviewVotes, colors }: DoughnutChartDetails) => {
  return (
    <S.DoughnutChartDetailList>
      {reviewVotes.map((reviewVote, index) => (
        <S.DetailItem key={reviewVote.content}>
          <S.ContentContainer>
            <S.ChartColor color={colors[index]}></S.ChartColor>
            <S.Description>{reviewVote.content}</S.Description>
          </S.ContentContainer>
          <span>{reviewVote.count}표</span>
        </S.DetailItem>
      ))}
    </S.DoughnutChartDetailList>
  );
};

export default DoughnutChartDetails;
