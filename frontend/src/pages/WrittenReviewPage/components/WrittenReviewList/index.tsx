import ReviewListItem from '@/components/ReviewListItem';

import { PageContentLayout } from '../layouts';

import * as S from './styles';

interface WrittenReviewListProps {
  handleClick: (reviewId: number) => void;
}

const WrittenReviewList = ({ handleClick }: WrittenReviewListProps) => {
  // 리뷰 리스트 받아오기
  const reviewIdList = [5, 1, 2, 3, 4];

  return (
    <PageContentLayout title="작성한 리뷰 목록">
      <S.WrittenReviewList>
        {/** 추후 이벤트 위임 형식으로 변경 가능 */}

        {/** TODO: 작성한 리뷰 없을 때의 컴포넌트 추가*/}
        {reviewIdList.map((reviewId) => (
          <ReviewListItem key={reviewId} handleClick={() => handleClick(reviewId)} />
        ))}
      </S.WrittenReviewList>
    </PageContentLayout>
  );
};

export default WrittenReviewList;
