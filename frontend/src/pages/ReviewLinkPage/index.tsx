import { ErrorSuspenseContainer } from '@/components';

import ReviewLinkDashboard from './components/ReviewLinkDashboard';

const ReviewLinkPage = () => {
  return (
    <ErrorSuspenseContainer>
      {/* TODO: 네비게이션 탭 추가 */}
      <ReviewLinkDashboard />
    </ErrorSuspenseContainer>
  );
};

export default ReviewLinkPage;
