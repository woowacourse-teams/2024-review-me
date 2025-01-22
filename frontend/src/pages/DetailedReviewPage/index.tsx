import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton, DetailedReview } from '@/components';

const DetailedReviewPage = () => {
  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <DetailedReview />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
