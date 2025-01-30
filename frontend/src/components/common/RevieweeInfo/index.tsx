import revieweeIcon from '@/assets/revieweeEmoji.png';

import * as S from './styles';

interface RevieweeInfo {
  revieweeName: string;
}

const RevieweeInfo = ({ revieweeName }: RevieweeInfo) => {
  return (
    <S.RevieweeInfo>
      <S.Guide>
        <img src={revieweeIcon} alt="" />
        <span>리뷰이</span>
      </S.Guide>
      | <S.RevieweeName>{revieweeName}</S.RevieweeName>
    </S.RevieweeInfo>
  );
};

export default RevieweeInfo;
