import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';
import { useSearchParamAndQuery } from '@/hooks';

import { useDeviceBreakpoints, useGetWrittenReviewList } from '../../hooks';
import EmptyWrittenReview from '../EmptyWrittenReview';
import { LargeContent, UnderLargeContent } from '../layouts';

const PageContent = () => {
  const { deviceType } = useDeviceBreakpoints();
  const { reviewList } = useGetWrittenReviewList();

  const { queryString: reviewIdString } = useSearchParamAndQuery({
    queryStringKey: 'reviewId',
  });

  const selectedReviewId = reviewIdString ? Number(reviewIdString) : null;

  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <TopButton />
      {reviewList.length === 0 ? (
        <EmptyWrittenReview />
      ) : deviceType.isDesktop ? (
        <LargeContent selectedReviewId={selectedReviewId} />
      ) : (
        <UnderLargeContent selectedReviewId={selectedReviewId} />
      )}
    </ErrorSuspenseContainer>
  );
};

export default PageContent;
