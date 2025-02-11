import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';
import NavigationTab from '@/components/common/NavigationTab';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';
import useCheckMemberUrl from '@/hooks/reviewGroup/useCheckMemberUrl';

import ReviewListPageContents from './components/ReviewListPageContents';

const ReviewListPage = () => {
  const { isMemberUrl } = useCheckMemberUrl();

  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      {isMemberUrl && <NavigationTab selectedTab="리뷰 링크 관리" />}
      <ReviewDisplayLayout isReviewList={true} isBackButton={isMemberUrl}>
        <ReviewListPageContents />
      </ReviewDisplayLayout>
    </ErrorSuspenseContainer>
  );
};

export default ReviewListPage;
