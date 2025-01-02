// 임시 컴포넌트! 작성한 리뷰 확인 && 받은 리뷰 확인 아이템

import * as S from './styles';

interface ReviewListItemProps {
  handleClick: () => void;
}

const ReviewListItem = ({ handleClick }: ReviewListItemProps) => {
  return <S.ReviewListItem onClick={handleClick}>리뷰 목록 아이템입니다</S.ReviewListItem>;
};

export default ReviewListItem;
