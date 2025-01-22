import { DetailedReview, BackButton, TopButton } from '@/components';
import { ROUTE } from '@/constants';

import { useDeviceBreakpoints } from '../../hooks';
import { NoSelectedReviewGuide } from '../index';
import { PageContentLayout } from '../layouts';

import * as S from './styles';

export interface DetailedWrittenReviewProps {
  $isDisplayable: boolean;
  selectedReviewId: number | null;
}

const DetailedWrittenReview = ({ $isDisplayable, selectedReviewId }: DetailedWrittenReviewProps) => {
  const { deviceType } = useDeviceBreakpoints();

  return (
    <PageContentLayout title={deviceType.isDesktop ? '작성한 리뷰 상세보기' : ''}>
      <S.DetailedWrittenReview $isDisplayable={$isDisplayable}>
        {!deviceType.isDesktop && (
          <>
            <BackButton prevPath={`/${ROUTE.writtenReview}`} wrapperStyle={{ marginBottom: '2rem' }} />
            <TopButton />
          </>
        )}

        <S.Outline>
          {selectedReviewId ? (
            <DetailedReview
              selectedReviewId={selectedReviewId}
              $layoutStyle={{
                width: '100%',
                height: '100%',
                marginTop: '0',
                padding: '3rem 1.5rem',
                border: 'none',
              }}
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
