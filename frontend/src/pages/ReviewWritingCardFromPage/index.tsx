import { ErrorSuspenseContainer } from '@/components';

import CardForm from './components/CardForm';

const ReviewWritingCardFormPage = () => {
  return (
    <ErrorSuspenseContainer>
      <CardForm />
    </ErrorSuspenseContainer>
  );
};
export default ReviewWritingCardFormPage;
