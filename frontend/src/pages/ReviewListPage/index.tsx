import { useRecoilValue } from 'recoil';

import { ErrorSuspenseContainer, LoginRedirectModal } from '@/components';
import { useGroupAccessCode } from '@/hooks';
import { reviewRequestCodeAtom } from '@/recoil';

import PageContents from './components/PageContents';

const ReviewListPage = () => {
  const { groupAccessCode } = useGroupAccessCode();
  const storedReviewRequestCode = useRecoilValue(reviewRequestCodeAtom);

  return (
    <>
      {groupAccessCode && storedReviewRequestCode ? (
        <ErrorSuspenseContainer>
          <PageContents groupAccessCode={groupAccessCode} reviewRequestCode={storedReviewRequestCode} />
        </ErrorSuspenseContainer>
      ) : (
        <LoginRedirectModal />
      )}
    </>
  );
};

export default ReviewListPage;
