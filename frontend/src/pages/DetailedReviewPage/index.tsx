import { ErrorSuspenseContainer } from '@/components';
import { useGroupAccessCode } from '@/hooks';

import { DetailedReviewPageContents } from './components';

const DetailedReviewPage = () => {
  const { groupAccessCode } = useGroupAccessCode();

  if (!groupAccessCode) return;

  return (
    <ErrorSuspenseContainer>
      <DetailedReviewPageContents groupAccessCode={groupAccessCode} />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
