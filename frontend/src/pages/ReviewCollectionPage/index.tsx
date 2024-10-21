import { AuthAndServerErrorFallback, ErrorSuspenseContainer, TopButton } from '@/components';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';

import ReviewCollectionPageContents from './components/ReviewCollectionPageContents';

const ReviewCollectionPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewDisplayLayout isReviewList={false}>
        <ReviewCollectionPageContents />
        <TopButton />
      </ReviewDisplayLayout>
    </ErrorSuspenseContainer>
  );
};

export default ReviewCollectionPage;
