import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';

import { DetailedReviewPageContents } from './components';

const DetailedReviewPage = () => {
  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <DetailedReviewPageContents />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
