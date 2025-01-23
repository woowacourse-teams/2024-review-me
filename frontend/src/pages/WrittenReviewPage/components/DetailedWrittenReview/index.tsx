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

const DetailedWrittenReview = ({ $isDisplayable, selectedReviewId }: DetailedWrittenReviewProps) => {
  const { deviceType } = useDeviceBreakpoints();

  const renderMobileItems = () =>
    !deviceType.isDesktop && (
      <>
        <BackButton prevPath={`/${ROUTE.writtenReview}`} wrapperStyle={{ marginBottom: '2rem' }} />
        <TopButton />
      </>
    );

  const renderDetailedReview = () =>
    selectedReviewId ? (
      <DetailedReview selectedReviewId={selectedReviewId} $layoutStyle={detailedReviewLayoutStyle} />
    ) : (
      <NoSelectedReviewGuide />
    );

  return (
    <PageContentLayout title={deviceType.isDesktop ? '작성한 리뷰 상세보기' : ''}>
      <S.DetailedWrittenReview $isDisplayable={$isDisplayable}>
        {renderMobileItems()}
        <S.Outline>
          <Suspense fallback={<LoadingBar />}>{renderDetailedReview()}</Suspense>
        </S.Outline>
      </S.DetailedWrittenReview>
    </PageContentLayout>
  );
};

const detailedReviewLayoutStyle = {
  width: '100%',
  height: '100%',
  marginTop: '0',
  padding: '3rem 1.5rem',
  border: 'none',
};

export default DetailedWrittenReview;
