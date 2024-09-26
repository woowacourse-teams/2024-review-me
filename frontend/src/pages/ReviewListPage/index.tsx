import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';

import PageContents from './components/PageContents';

const ReviewListPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <PageContents />
    </ErrorSuspenseContainer>
  );
};

export default ReviewListPage;
