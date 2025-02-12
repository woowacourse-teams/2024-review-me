import { AuthAndServerErrorFallback, ErrorSuspenseContainer } from '@/components';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';

import ReviewCollectionPageContents from './components/ReviewCollectionPageContents';

const ReviewCollectionPage = () => {
  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <ReviewDisplayLayout isReviewList={false}>
        <ReviewCollectionPageContents />
      </ReviewDisplayLayout>
    </ErrorSuspenseContainer>
  );
};

export default ReviewCollectionPage;
