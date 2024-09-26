import { calculateParticle } from '@/utils';

import * as S from './styles';

interface ReviewInfoSectionProps {
  projectName: string;
  revieweeName: string;
}

const ReviewInfoSection = ({ projectName, revieweeName }: ReviewInfoSectionProps) => {
  const reviewMessageSuffix = `${calculateParticle({ target: revieweeName, particles: { withFinalConsonant: '이', withoutFinalConsonant: '가' } })} 받은 리뷰 목록이에요`;

  return (
    <S.ReviewInfoContainer>
      <S.ProjectName>{projectName}</S.ProjectName>
      <S.RevieweeInfoWrapper>
        <S.RevieweeName>{revieweeName}</S.RevieweeName>
        {reviewMessageSuffix}
      </S.RevieweeInfoWrapper>
    </S.ReviewInfoContainer>
  );
};

export default ReviewInfoSection;
