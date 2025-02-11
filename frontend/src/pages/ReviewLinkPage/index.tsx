import { ErrorSuspenseContainer } from '@/components';
import NavigationTab from '@/components/common/NavigationTab';

import ReviewLinkDashboard from './components/ReviewLinkDashboard';

const ReviewLinkPage = () => {
  return (
    <ErrorSuspenseContainer>
      <NavigationTab selectedTab="리뷰 링크 관리" />
      <ReviewLinkDashboard />
    </ErrorSuspenseContainer>
  );
};

export default ReviewLinkPage;
