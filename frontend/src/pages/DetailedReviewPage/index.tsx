import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';

import { DetailedReviewPageContents } from './components';

const DetailedReviewPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <DetailedReviewPageContents />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
