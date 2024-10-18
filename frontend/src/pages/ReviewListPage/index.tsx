import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';

import ReviewListPageContents from './components/PageContents';

const ReviewListPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewListPageContents />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default ReviewListPage;
