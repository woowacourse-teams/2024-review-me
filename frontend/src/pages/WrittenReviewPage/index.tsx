import { ErrorSuspenseContainer } from '@/components';

import PageContent from './components/PageContent';

const WrittenReviewPage = () => {
  return (
    <ErrorSuspenseContainer>
      <PageContent />
    </ErrorSuspenseContainer>
  );
};

export default WrittenReviewPage;
