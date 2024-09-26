import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';

import PageContents from './components/PageContents';

const ReviewListPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <PageContents />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default ReviewListPage;
