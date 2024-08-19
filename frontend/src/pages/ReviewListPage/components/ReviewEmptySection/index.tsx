import * as S from './styles';

const ReviewEmptySection = () => {
  return (
    <S.Container>
      <S.NullText>널~</S.NullText>
      <S.EmptyReviewsText>아직 받은 리뷰가 없어요!</S.EmptyReviewsText>
    </S.Container>
  );
};

export default ReviewEmptySection;
