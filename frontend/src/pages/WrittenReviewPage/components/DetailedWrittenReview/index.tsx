import { Suspense } from 'react';

import { DetailedReview, BackButton, TopButton, LoadingBar } from '@/components';
import { ROUTE } from '@/constants';

import { useDeviceBreakpoints } from '../../hooks';
import { NoSelectedReviewGuide } from '../index';
import { PageContentLayout } from '../layouts';

import * as S from './styles';

export interface DetailedWrittenReviewProps {
  $isDisplayable: boolean;
  selectedReviewId: number | null;
}

const detailedReviewLayoutStyle = {
  width: '100%',
  height: '100%',
  marginTop: '0',
  padding: '3rem 1.5rem',
  border: 'none',
};

const ResponsiveUtils = () => {
  return (
    <>
      <BackButton prevPath={`/${ROUTE.writtenReview}`} wrapperStyle={{ marginBottom: '2rem' }} />
      <TopButton />
    </>
  );
};

const DetailedWrittenReview = ({ $isDisplayable, selectedReviewId }: DetailedWrittenReviewProps) => {
  const { deviceType } = useDeviceBreakpoints();

  return (
    <PageContentLayout title={deviceType.isDesktop ? '작성한 리뷰 상세보기' : ''}>
      <S.DetailedWrittenReview $isDisplayable={$isDisplayable}>
        {!deviceType.isDesktop && <ResponsiveUtils />}

        <S.Outline>
          <Suspense fallback={<LoadingBar />}>
            {selectedReviewId ? (
              <DetailedReview selectedReviewId={selectedReviewId} $layoutStyle={detailedReviewLayoutStyle} />
            ) : (
              <NoSelectedReviewGuide />
            )}
          </Suspense>
        </S.Outline>
      </S.DetailedWrittenReview>
    </PageContentLayout>
  );
};

export default DetailedWrittenReview;
