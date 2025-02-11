import { ErrorSuspenseContainer, AuthAndServerErrorFallback, TopButton } from '@/components';
import BackButton from '@/components/common/BackButton';
import NavigationTab from '@/components/common/NavigationTab';
import { ROUTE } from '@/constants';
import useCheckMemberUrl from '@/hooks/reviewGroup/useCheckMemberUrl';

import { DetailedReviewPageContents } from './components';
import * as S from './styles';

const DetailedReviewPage = () => {
  const { isMemberUrl, reviewRequestCode } = useCheckMemberUrl();

  return (
    <ErrorSuspenseContainer errorFallback={AuthAndServerErrorFallback}>
      {isMemberUrl && <NavigationTab selectedTab="리뷰 링크 관리" />}
      <S.PageWithBackButton>
        {isMemberUrl && <BackButton prevPath={`/${ROUTE.reviewList}/${reviewRequestCode}`} />}
        <DetailedReviewPageContents />
      </S.PageWithBackButton>
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default DetailedReviewPage;
