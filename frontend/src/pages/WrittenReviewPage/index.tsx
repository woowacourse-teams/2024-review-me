import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';
import { useSearchParamAndQuery } from '@/hooks';
import { useGetUserProfile } from '@/hooks/oAuth';

import { EmptyWrittenReview } from './components';
import { LargeContent, UnderLargeContent } from './components/layouts';
import { useDeviceBreakpoints, useGetWrittenReviewList } from './hooks';

const WrittenReviewPage = () => {
  const { userProfile, isUserLoggedIn } = useGetUserProfile();
  const memberIdProp = isUserLoggedIn && userProfile ? userProfile.memberId : undefined;

  const { deviceType } = useDeviceBreakpoints();
  const { reviewList } = useGetWrittenReviewList({ memberId: memberIdProp });

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

export default WrittenReviewPage;
