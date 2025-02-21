import { DetailedWrittenReview } from '../..';
import WrittenReviewList from '../../WrittenReviewList';

import * as S from './styles';

interface LargeContentProps {
  selectedReviewId: number | null;
}

// Large 이상: 목록, 상세 모두 렌더링
const LargeContent = ({ selectedReviewId }: LargeContentProps) => {
  return (
    <S.LargeContentContainer>
      <WrittenReviewList />
      <DetailedWrittenReview $isDisplayable={true} selectedReviewId={selectedReviewId} />
    </S.LargeContentContainer>
  );
};

export default LargeContent;
