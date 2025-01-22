import { DetailedReview, BackButton } from '@/components';
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
          <BackButton prevPath={`/${ROUTE.writtenReview}`} wrapperStyle={{ marginBottom: '2rem' }} />
        )}
        <S.Outline>
          {selectedReviewId ? (
            <S.ContentContainer>
              <DetailedReview
                selectedReviewId={selectedReviewId}
                $layoutStyle={{ width: '100%', height: '100%', marginTop: '0', border: 'none' }}
              />
            </S.ContentContainer>
          ) : (
            <NoSelectedReviewGuide />
          )}
        </S.Outline>
      </S.DetailedWrittenReview>
    </PageContentLayout>
  );
};

export default DetailedWrittenReview;
