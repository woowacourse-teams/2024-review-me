import { NoSelectedReviewGuide } from '../index';
import { PageContentLayout } from '../layouts';

import * as S from './styles';

export interface DetailedWrittenReviewProps {
  $isDisplayable: boolean;
  selectedReviewId: number | null;
}

const DetailedWrittenReview = ({ $isDisplayable, selectedReviewId }: DetailedWrittenReviewProps) => {
  // 추후 이곳에서 직접 상세 리뷰 데이터 호출

  return (
    <PageContentLayout title="작성한 리뷰 상세보기">
      <S.DetailedWrittenReview $isDisplayable={$isDisplayable}>
        <S.Outline>{selectedReviewId ? <div>{selectedReviewId} 선택함 </div> : <NoSelectedReviewGuide />}</S.Outline>
      </S.DetailedWrittenReview>
    </PageContentLayout>
  );
};

export default DetailedWrittenReview;
