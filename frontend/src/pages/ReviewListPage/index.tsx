import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';

import ReviewListPageContents from './components/ReviewListPageContents';

const ReviewListPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewDisplayLayout isReviewList={true}>
        <ReviewListPageContents />
      </ReviewDisplayLayout>
    </ErrorSuspenseContainer>
  );
};

export default ReviewListPage;
