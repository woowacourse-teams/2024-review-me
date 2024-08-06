import { ErrorSuspenseContainer, LoginRedirectModal } from '@/components';
import { useGroupAccessCode } from '@/hooks';

import { DetailedReviewPageContents } from './components';

const DetailedReviewPage = () => {
  const { groupAccessCode } = useGroupAccessCode();

  if (!groupAccessCode) return <LoginRedirectModal />;

  return (
    <ErrorSuspenseContainer>
      <DetailedReviewPageContents groupAccessCode={groupAccessCode} />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
