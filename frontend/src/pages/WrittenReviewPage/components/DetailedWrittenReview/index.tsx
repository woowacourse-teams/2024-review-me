import { DetailedReview } from '@/components';

import { NoSelectedReviewGuide } from '../index';
import { PageContentLayout } from '../layouts';

import * as S from './styles';

export interface DetailedWrittenReviewProps {
  $isDisplayable: boolean;
  selectedReviewId: number | null;
}

const DetailedWrittenReview = ({ $isDisplayable, selectedReviewId }: DetailedWrittenReviewProps) => {
  return (
    <PageContentLayout title="작성한 리뷰 상세보기">
      <S.DetailedWrittenReview $isDisplayable={$isDisplayable}>
        <S.Outline>
          {selectedReviewId ? (
            <DetailedReview
              selectedReviewId={selectedReviewId}
              $layoutStyle={{ width: '100%', height: '100%', marginTop: '0', marginBottom: '2rem', border: 'none' }}
            />
          ) : (
            <NoSelectedReviewGuide />
          )}
        </S.Outline>
      </S.DetailedWrittenReview>
    </PageContentLayout>
  );
};

export default DetailedWrittenReview;
