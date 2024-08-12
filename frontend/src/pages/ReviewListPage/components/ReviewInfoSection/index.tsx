import * as S from './styles';

interface ReviewInfoSectionProps {
  projectName: string;
  revieweeName: string;
}

const REVIEW_MESSAGE_SUFFIX = '님에게 달린 리뷰입니다!';

const ReviewInfoSection = ({ projectName, revieweeName }: ReviewInfoSectionProps) => {
  return (
    <S.ReviewInfoContainer>
      <S.ProjectName>{projectName}</S.ProjectName>
      <S.RevieweeInfoWrapper>
        <S.RevieweeName>{revieweeName}</S.RevieweeName>
        {REVIEW_MESSAGE_SUFFIX}
      </S.RevieweeInfoWrapper>
    </S.ReviewInfoContainer>
  );
};

export default ReviewInfoSection;
