import { DetailedWrittenReview, WrittenReviewList } from '../..';

interface UnderLargeContentProps {
  selectedReviewId: number | null;
}

// large 사이즈 이하: queryString 없으면 목록, 있으면 상세보기 렌더링
const UnderLargeContent = ({ selectedReviewId }: UnderLargeContentProps) => {
  return selectedReviewId ? (
    <DetailedWrittenReview $isDisplayable={!!selectedReviewId} selectedReviewId={selectedReviewId} />
  ) : (
    <WrittenReviewList />
  );
};

export default UnderLargeContent;
