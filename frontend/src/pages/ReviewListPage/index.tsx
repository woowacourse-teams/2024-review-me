import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';

import ReviewListPageContents from './components/ReviewListPageContents';

const ReviewListPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewListPageContents />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default ReviewListPage;
