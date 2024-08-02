import { ErrorSuspenseContainer, LoginRedirectModal } from '@/components';
import { useGroupAccessCode } from '@/hooks';

import { DetailedReviewPageContents } from './components';

const DetailedReviewPage = () => {
  const { groupAccessCode } = useGroupAccessCode();

  return (
    <>
      {groupAccessCode ? (
        <ErrorSuspenseContainer>
          <DetailedReviewPageContents groupAccessCode={groupAccessCode} />
        </ErrorSuspenseContainer>
      ) : (
        <LoginRedirectModal />
      )}
    </>
  );
};

export default DetailedReviewPage;
