import { AuthAndServerErrorFallback, ErrorSuspenseContainer } from '@/components';
import NavigationTab from '@/components/common/NavigationTab';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';
import useCheckMemberUrl from '@/hooks/reviewGroup/useCheckMemberUrl';

import ReviewCollectionPageContents from './components/ReviewCollectionPageContents';

const ReviewCollectionPage = () => {
  const { isMemberUrl } = useCheckMemberUrl();

  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      {isMemberUrl && <NavigationTab selectedTab="리뷰 링크 관리" />}
      <ReviewDisplayLayout isReviewList={false} isBackButton={isMemberUrl}>
        <ReviewCollectionPageContents />
      </ReviewDisplayLayout>
    </ErrorSuspenseContainer>
  );
};

export default ReviewCollectionPage;
