import { TopButton, OptionSwitch } from '@/components/common';
import BackButton from '@/components/common/BackButton';
import { ROUTE } from '@/constants';
import { useGetUserProfile } from '@/hooks/oAuth';
import { EssentialPropsWithChildren } from '@/types';

import ReviewInfoSection from './components/ReviewInfoSection';
import { useReviewDisplayLayoutOptions } from './hooks';
import { ReviewInfoDataProvider } from './ReviewInfoDataProvider';
import * as S from './styles';

interface ReviewDisplayLayoutProps extends EssentialPropsWithChildren {
  isReviewList: boolean;
}

const ReviewDisplayLayout = ({ isReviewList, children }: ReviewDisplayLayoutProps) => {
  const reviewDisplayLayoutOptions = useReviewDisplayLayoutOptions();
  const { isUserLoggedIn } = useGetUserProfile();

  return (
    <ReviewInfoDataProvider>
      <S.ReviewDisplayLayoutContainer>
        {isUserLoggedIn && <BackButton prevPath={`/${ROUTE.reviewLinks}`} />}
        <S.Container>
          <ReviewInfoSection isReviewList={isReviewList} />
          <OptionSwitch options={reviewDisplayLayoutOptions} />
        </S.Container>
        <TopButton />
        {children}
      </S.ReviewDisplayLayoutContainer>
    </ReviewInfoDataProvider>
  );
};

export default ReviewDisplayLayout;
