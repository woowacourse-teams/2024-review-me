import { AuthAndServerErrorFallback, ErrorSuspenseContainer, TopButton } from '@/components';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';
import { useGetReviewList } from '@/hooks';

const ReviewCollectionPage = () => {
  // TODO: 추후 리뷰 그룹 정보를 받아오는 API로 대체
  const { data } = useGetReviewList();
  const { revieweeName, projectName } = data.pages[0];

  return (
    <ErrorSuspenseContainer fallback={AuthAndServerErrorFallback}>
      <ReviewDisplayLayout projectName={projectName} revieweeName={revieweeName} isReviewList={false}>
        <div>리뷰 모아보기 페이지 children</div>
        {Array(50)
          .fill('스크롤바 없어서 생기는 layout shift 방지용 + Topbutton 확인용 더미 데이터입니다.')
          .map((data, index) => {
            return <p key={index}>{data}</p>;
          })}
      </ReviewDisplayLayout>
      <TopButton />
    </ErrorSuspenseContainer>
  );
};

export default ReviewCollectionPage;
