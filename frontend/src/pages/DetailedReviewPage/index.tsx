import {
  ErrorSuspenseContainer,
  AuthAndServerErrorFallback,
  TopButton,
  DetailedReview,
  BackButton,
} from '@/components';
import { ROUTE } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';
import { useGetUserProfile } from '@/hooks/oAuth';

import * as S from './styles';

const detailedReviewLayoutStyle = {
  width: '100%',
  margin: '2rem 0',
};

const DetailedReviewPage = () => {
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const { isUserLoggedIn } = useGetUserProfile();

  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <S.PageWithBackButton>
        {isUserLoggedIn && <BackButton prevPath={`/${ROUTE.reviewList}/${reviewRequestCode}`} />}
        <DetailedReview $layoutStyle={detailedReviewLayoutStyle} />
      </S.PageWithBackButton>
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
