import * as S from './styles';

interface RevieweeNameProps {
  revieweeTitle: string;
  revieweeName: string;
}

const RevieweeName = ({ revieweeTitle, revieweeName }: RevieweeNameProps) => {
  return (
    <S.RevieweeNameWrapper>
      {revieweeTitle} | {revieweeName}
    </S.RevieweeNameWrapper>
  );
};

export default RevieweeName;
