import { ErrorSuspenseContainer, AuthAndServerErrorFallback } from '@/components';

import PageContent from './components/PageContent';

const WrittenReviewPage = () => {
  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <PageContent />
    </ErrorSuspenseContainer>
  );
};

export default WrittenReviewPage;
