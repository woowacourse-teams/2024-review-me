import * as S from './styles';

interface ReviewEmptySectionProps {
  content: string;
}

const ReviewEmptySection = ({ content }: ReviewEmptySectionProps) => {
  return (
    <S.Container>
      <S.NullText>널~</S.NullText>
      <S.EmptyReviewsText>{content}</S.EmptyReviewsText>
    </S.Container>
  );
};

export default ReviewEmptySection;
