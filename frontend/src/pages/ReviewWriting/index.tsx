import { ErrorSuspenseContainer } from '@/components';

import ReviewWritingContents from './components/ReviewWritingContents';

const ReviewWritingPage = () => {
  return (
    <ErrorSuspenseContainer>
      <ReviewWritingContents />
    </ErrorSuspenseContainer>
  );
};

export default ReviewWritingPage;
