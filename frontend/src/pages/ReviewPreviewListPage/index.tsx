import { ErrorSuspenseContainer, LoginRedirectModal } from '@/components';
import { useGroupAccessCode } from '@/hooks';

import PageContents from './components/PageContents';

const ReviewPreviewListPage = () => {
  const { groupAccessCode } = useGroupAccessCode();

  return (
    <>
      {groupAccessCode ? (
        <ErrorSuspenseContainer>
          <PageContents groupAccessCode={groupAccessCode} />
        </ErrorSuspenseContainer>
      ) : (
        <LoginRedirectModal />
      )}
    </>
  );
};

export default ReviewPreviewListPage;
