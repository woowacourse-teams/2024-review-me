import { ErrorSuspenseContainer } from '@/components';

import CardForm from './components/CardForm';

const ReviewWritingPage = () => {
  return (
    <ErrorSuspenseContainer>
      <CardForm />
    </ErrorSuspenseContainer>
  );
};
export default ReviewWritingPage;
