import { useRecoilValue } from 'recoil';

import { ErrorSuspenseContainer, LoginRedirectModal } from '@/components';
import { groupAccessCodeAtom } from '@/recoil';

import { DetailedReviewPageContents } from './components';

const DetailedReviewPage = () => {
  const groupAccessCode = useRecoilValue(groupAccessCodeAtom);

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
