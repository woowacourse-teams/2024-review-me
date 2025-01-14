import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';

import { DetailedReviewPageContents } from './components';

const DetailedReviewPage = () => {
  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <DetailedReviewPageContents />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
