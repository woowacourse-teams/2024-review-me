import { calculateParticle } from '@/utils';

import * as S from './styles';

export interface ReviewInfoSectionProps {
  revieweeName: string;
  isReviewList: boolean;
  projectName: string;
  reviewCount?: number;
}

const ReviewInfoSection = ({ projectName, revieweeName, reviewCount, isReviewList }: ReviewInfoSectionProps) => {
  const revieweeNameWithParticle = calculateParticle({
    target: revieweeName,
    particles: { withFinalConsonant: '이', withoutFinalConsonant: '가' },
  });

  const getReviewInfoMessage = () => {
    return isReviewList
      ? `${revieweeNameWithParticle} 받은 ${reviewCount}개의 리뷰 목록이에요`
      : `${revieweeNameWithParticle} 받은 리뷰를 질문별로 모아봤어요`;
  };

  return (
    <S.ReviewInfoContainer>
      <S.ProjectName>{projectName}</S.ProjectName>
      <S.RevieweeInfoWrapper>
        <S.RevieweeName>{revieweeName}</S.RevieweeName>
        {getReviewInfoMessage()}
      </S.RevieweeInfoWrapper>
    </S.ReviewInfoContainer>
  );
};

export default ReviewInfoSection;
