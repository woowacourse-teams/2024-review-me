import { TopButton, OptionSwitch } from '@/components/common';
import { EssentialPropsWithChildren } from '@/types';

import ReviewInfoSection from './components/ReviewInfoSection';
import { useReviewDisplayLayoutOptions } from './hooks';
import { ReviewInfoDataProvider } from './ReviewInfoDataProvider';
import * as S from './styles';

interface ReviewDisplayLayoutProps extends EssentialPropsWithChildren {
  isReviewList: boolean;
}

const ReviewDisplayLayoutContent = ({ isReviewList, children }: ReviewDisplayLayoutProps) => {
  const reviewDisplayLayoutOptions = useReviewDisplayLayoutOptions();

  return (
    <S.ReviewDisplayLayout>
      <S.Container>
        <ReviewInfoSection isReviewList={isReviewList} />
        <OptionSwitch options={reviewDisplayLayoutOptions} />
      </S.Container>
      <TopButton />
      {children}
    </S.ReviewDisplayLayout>
  );
};

const ReviewDisplayLayout = ({ isReviewList, children }: ReviewDisplayLayoutProps) => {
  return (
    <ReviewInfoDataProvider>
      <ReviewDisplayLayoutContent isReviewList={isReviewList}>{children}</ReviewDisplayLayoutContent>
    </ReviewInfoDataProvider>
  );
};

export default ReviewDisplayLayout;
