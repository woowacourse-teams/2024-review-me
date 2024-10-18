import { AuthAndServerErrorFallback, ErrorSuspenseContainer, TopButton } from '@/components';

import ReviewCollectionPageContents from './components/PageContents';

const ReviewCollectionPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewCollectionPageContents />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default ReviewCollectionPage;
