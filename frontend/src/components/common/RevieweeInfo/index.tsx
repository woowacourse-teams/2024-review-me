import RevieweeIcon from '@/assets/revieweeEmoji.png';

import * as S from './styles';

interface RevieweeInfo {
  revieweeName: string;
}

const RevieweeInfo = ({ revieweeName }: RevieweeInfo) => {
  return (
    <S.RevieweeInfo>
      <S.Guide>
        <img src={RevieweeIcon} alt="" />
        <span>리뷰이</span>
      </S.Guide>
      <S.Separator>|</S.Separator>
      <S.RevieweeName>{revieweeName}</S.RevieweeName>
    </S.RevieweeInfo>
  );
};

export default RevieweeInfo;
