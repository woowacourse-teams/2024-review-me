import { TopButton, OptionSwitch } from '@/components/common';
import BackButton from '@/components/common/BackButton';
import { ROUTE } from '@/constants';
import { EssentialPropsWithChildren } from '@/types';

import ReviewInfoSection from './components/ReviewInfoSection';
import { useReviewDisplayLayoutOptions } from './hooks';
import { ReviewInfoDataProvider } from './ReviewInfoDataProvider';
import * as S from './styles';

interface ReviewDisplayLayoutProps extends EssentialPropsWithChildren {
  isReviewList: boolean;
  isBackButton: boolean;
}

const ReviewDisplayLayout = ({ isReviewList, isBackButton, children }: ReviewDisplayLayoutProps) => {
  const reviewDisplayLayoutOptions = useReviewDisplayLayoutOptions();

  return (
    <ReviewInfoDataProvider>
      <S.ReviewDisplayLayoutContainer>
        {isBackButton && <BackButton prevPath={`/${ROUTE.reviewLinks}`} />}
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
