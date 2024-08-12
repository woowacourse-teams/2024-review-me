import { ErrorSuspenseContainer } from '@/components';

import CardForm from './CardForm';
// TODO : API 연결 후 ReviewWriting 폴더 삭제
const ReviewWritingCardFormPage = () => {
  return (
    <ErrorSuspenseContainer>
      <CardForm />
    </ErrorSuspenseContainer>
  );
};
export default ReviewWritingCardFormPage;
