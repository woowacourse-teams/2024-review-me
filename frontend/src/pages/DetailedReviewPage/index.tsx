import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton, DetailedReview } from '@/components';

const DetailedReviewPage = () => {
  // TODO: 임시로 true 설정 (로그인 기능 추가하면서 여기도 수정해야 한다.)
  const isUserLoggedIn = true;
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <DetailedReview />
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
