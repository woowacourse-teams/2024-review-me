import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';
import BackButton from '@/components/common/BackButton';
import { ROUTE } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';

import { DetailedReviewPageContents } from './components';
import * as S from './styles';

const DetailedReviewPage = () => {
  // TODO: 임시로 true 설정 (로그인 기능 추가하면서 여기도 수정해야 한다.)
  const isUserLoggedIn = true;
  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      <S.PageWithBackButton>
        {isUserLoggedIn && <BackButton prevPath={`/${ROUTE.reviewList}/${reviewRequestCode}`} />}
        <DetailedReviewPageContents />
      </S.PageWithBackButton>
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
