import { TopButton, OptionSwitch } from '@/components/common';
import { EssentialPropsWithChildren } from '@/types';

import ReviewInfoSection, { ReviewInfoSectionProps } from './components/ReviewInfoSection';
import useReviewDisplayLayoutOptions from './hooks/useReviewDisplayLayoutOptions';
import * as S from './styles';

const ReviewDisplayLayout = ({
  revieweeName,
  projectName,
  reviewCount,
  isReviewList,
  children,
}: EssentialPropsWithChildren<ReviewInfoSectionProps>) => {
  const reviewDisplayLayoutOptions = useReviewDisplayLayoutOptions();

  return (
    <S.ReviewDisplayLayout>
      <S.Container>
        <ReviewInfoSection
          revieweeName={revieweeName}
          projectName={projectName}
          reviewCount={reviewCount}
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
