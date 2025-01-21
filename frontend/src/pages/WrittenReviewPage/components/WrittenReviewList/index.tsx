import { ReviewCard } from '@/components';
import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { useInfiniteScroll } from '@/pages/ReviewListPage/hooks'; // 경로 수정하기

import { useGetWrittenReviewList } from '../../hooks';
import { PageContentLayout } from '../layouts';

import * as S from './styles';

interface WrittenReviewListProps {
  handleClick: (reviewId: number) => void;
}

const WrittenReviewList = ({ handleClick }: WrittenReviewListProps) => {
  const { data, fetchNextPage, isLoading, isSuccess } = useGetWrittenReviewList();

  const isLastPage = data.pages[data.pages.length - 1].isLastPage;
  const reviewList = data.pages.flatMap((page) => page.reviews) || [];

  const lastReviewElementRef = useInfiniteScroll({
    fetchNextPage,
    isLoading,
    isLastPage,
  });

  return (
    <PageContentLayout title="작성한 리뷰 목록">
      {isSuccess && (
        <S.WrittenReviewList>
          {/** TODO: 추후 이벤트 위임 형식으로 변경  */}
          {reviewList.map((review, index) => {
            const isLastReview = reviewList.length === index + 1;

            // TODO: ReviewCard 확장. 프로젝트 이름, 리뷰이 이름 추가
            return (
              <UndraggableWrapper key={review.reviewId}>
                <ReviewCard
                  createdAt={review.createdAt}
                  contentPreview={review.contentPreview}
                  categories={review.categories}
                  handleClick={() => handleClick(review.reviewId)}
                />
                <div ref={isLastReview ? lastReviewElementRef : null} style={{ height: '0.1rem' }} />
              </UndraggableWrapper>
            );
          })}
        </S.WrittenReviewList>
      )}
    </PageContentLayout>
  );
};

export default WrittenReviewList;
