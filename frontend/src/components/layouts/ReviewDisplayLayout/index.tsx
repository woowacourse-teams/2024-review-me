import { useLocation, useNavigate } from 'react-router';

import { TopButton, OptionSwitch } from '@/components/common';
import { ROUTE } from '@/constants/route';
import { useSearchParamAndQuery } from '@/hooks';
import { EssentialPropsWithChildren } from '@/types';

import ReviewInfoSection, { ReviewInfoSectionProps } from './components/ReviewInfoSection';
import * as S from './styles';

export interface ReviewDisplayLayoutProps extends ReviewInfoSectionProps {}

const ReviewDisplayLayout = ({
  revieweeName,
  projectName,
  reviewCount,
  isReviewList,
  children,
}: EssentialPropsWithChildren<ReviewInfoSectionProps>) => {
  const { pathname } = useLocation();
  const navigate = useNavigate();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const isReviewCollectionURL = (url: string) => {
    return url.includes(ROUTE.reviewCollection);
  };

  const handleSwitchToggle = () => {
    isReviewCollectionURL(pathname)
      ? navigate(`/${ROUTE.reviewList}/${reviewRequestCode}`)
      : navigate(`/${ROUTE.reviewCollection}/${reviewRequestCode}`);
  };

  return (
    <S.ReviewDisplayLayout>
      <S.Container>
        <ReviewInfoSection
          revieweeName={revieweeName}
          projectName={projectName}
          reviewCount={reviewCount}
          isReviewList={isReviewList}
        />
        <OptionSwitch
          leftLabel="목록보기"
          rightLabel="모아보기"
          isReviewList={isReviewList}
          handleSwitchClick={handleSwitchToggle}
        />
      </S.Container>
      <TopButton />
      {children}
    </S.ReviewDisplayLayout>
  );
};

export default ReviewDisplayLayout;
