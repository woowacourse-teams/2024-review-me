import { ErrorSuspenseContainer } from '@/components';
import NavigationTab from '@/components/common/NavigationTab';

import ReviewLinkDashboard from './components/ReviewLinkDashboard';

const ReviewLinkPage = () => {
  return (
    <ErrorSuspenseContainer>
      <NavigationTab />
      <ReviewLinkDashboard />
    </ErrorSuspenseContainer>
  );
};

export default ReviewLinkPage;
