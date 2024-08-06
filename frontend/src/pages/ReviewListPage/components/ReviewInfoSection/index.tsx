import * as S from './styles';

interface ReviewInfoSectionProps {
  projectName: string;
  revieweeName: string;
}

const ReviewInfoSection = ({ projectName, revieweeName }: ReviewInfoSectionProps) => {
  return (
    <S.Container>
      <S.ProjectName>{projectName}</S.ProjectName>
      <S.RevieweeName>{revieweeName}</S.RevieweeName>
    </S.Container>
  );
};

export default ReviewInfoSection;
