import { ErrorSuspenseContainer } from '@/components';

import ReviewLinkDashboard from './components/ReviewLinkDashboard';

const ReviewLinkPage = () => {
  return (
    <ErrorSuspenseContainer>
      <ReviewLinkDashboard />
    </ErrorSuspenseContainer>
  );
};

export default ReviewLinkPage;
