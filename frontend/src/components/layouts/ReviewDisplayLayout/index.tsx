import { TopButton, OptionSwitch } from '@/components/common';
import { EssentialPropsWithChildren } from '@/types';

import ReviewInfoSection from './components/ReviewInfoSection';
import { useReviewInfoData, useReviewDisplayLayoutOptions } from './hooks';
import * as S from './styles';

interface ReviewDisplayLayoutProps {
  isReviewList: boolean;
}

const ReviewDisplayLayout = ({ isReviewList, children }: EssentialPropsWithChildren<ReviewDisplayLayoutProps>) => {
  const reviewDisplayLayoutOptions = useReviewDisplayLayoutOptions();
  const { revieweeName, projectName, totalReviewCount } = useReviewInfoData();

  return (
    <S.ReviewDisplayLayout>
      <S.Container>
        <ReviewInfoSection
          revieweeName={revieweeName}
          projectName={projectName}
          totalReviewCount={totalReviewCount}
          isReviewList={isReviewList}
        />
        <OptionSwitch options={reviewDisplayLayoutOptions} />
      </S.Container>
      <TopButton />
      {children}
    </S.ReviewDisplayLayout>
  );
};

export default ReviewDisplayLayout;
