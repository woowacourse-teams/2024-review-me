import { AuthAndServerErrorFallback, ErrorSuspenseContainer, TopButton } from '@/components';

import ReviewCollectionPageContents from './components/ReviewCollectionPageContents';

const ReviewCollectionPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewCollectionPageContents />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default ReviewCollectionPage;
