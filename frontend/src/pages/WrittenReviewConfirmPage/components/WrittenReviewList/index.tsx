import WrittenReviewItem from '../../layouts/WrittenReviewItem';

import * as S from './styles';

interface WrittenReviewListProps {
  handleClick: (reviewId: number) => void;
}

const WrittenReviewList = ({ handleClick }: WrittenReviewListProps) => {
  return (
    <S.WrittenReviewList>
      <WrittenReviewItem title="작성한 리뷰 목록">
        <h2>My Posts</h2>
        <ul>
          {[5, 1, 2, 3].map((reviewId) => (
            <li key={reviewId} onClick={() => handleClick(reviewId)}>
              Post {reviewId}
            </li>
          ))}
        </ul>
      </WrittenReviewItem>
    </S.WrittenReviewList>
  );
};

export default WrittenReviewList;
