import SlideArrowsIcon from '@/assets/slideArrows.svg';

import * as S from './styles';

const NoSelectedReviewGuide = () => {
  return (
    <S.NoSelectedReview>
      <img src={SlideArrowsIcon} alt="" />
      <p>확인할 리뷰를 선택해주세요!</p>
    </S.NoSelectedReview>
  );
};

export default NoSelectedReviewGuide;
