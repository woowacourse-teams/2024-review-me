import * as S from './styles';

interface DescriptionSectionProps {
  projectName: string;
  revieweeName: string;
}

const DescriptionSection = ({ projectName, revieweeName }: DescriptionSectionProps) => {
  return (
    <S.Container>
      <S.ProjectName>{projectName}</S.ProjectName>
      <S.RevieweeName>{revieweeName}</S.RevieweeName>
    </S.Container>
  );
};

export default DescriptionSection;
