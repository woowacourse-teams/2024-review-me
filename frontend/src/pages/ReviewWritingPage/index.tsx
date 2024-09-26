import { ErrorSuspenseContainer } from '@/components';

import { CardForm } from './form/components';

const ReviewWritingPage = () => {
  return (
    <ErrorSuspenseContainer>
      <CardForm />
    </ErrorSuspenseContainer>
  );
};
export default ReviewWritingPage;
