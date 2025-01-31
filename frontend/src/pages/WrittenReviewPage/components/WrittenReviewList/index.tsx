import { useRef } from 'react';

import { ReviewPreview, TopButton } from '@/components';
import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { useInfiniteScroll } from '@/hooks';

import { useGetWrittenReviewList } from '../../hooks';
import { PageContentLayout } from '../layouts';

import * as S from './styles';

interface WrittenReviewListProps {
  handleClick: (reviewId: number) => void;
}

const WrittenReviewList = ({ handleClick }: WrittenReviewListProps) => {
  const { reviewList, isLastPage, fetchNextPage, isSuccess, isFetchingNextPage } = useGetWrittenReviewList();

  const containerRef = useRef<HTMLUListElement | null>(null);

  const lastReviewElementRef = useInfiniteScroll({
    fetchNextPage,
    isFetchingNextPage,
    isLastPage,
  });

  return (
    <PageContentLayout title="작성한 리뷰 목록">
      {isSuccess && (
        <S.WrittenReviewList ref={containerRef}>
          {reviewList.map((review) => (
            <UndraggableWrapper key={review.reviewId}>
              <ReviewPreview
                createdAt={review.createdAt}
                contentPreview={review.contentPreview}
                categories={review.categories}
                projectName={review.projectName}
                revieweeName={review.revieweeName}
                handleClick={() => handleClick(review.reviewId)}
              />
              {!isFetchingNextPage && !isLastPage && <div ref={lastReviewElementRef} style={{ height: '0.1rem' }} />}
            </UndraggableWrapper>
          ))}
          <TopButton containerRef={containerRef} />
        </S.WrittenReviewList>
      )}
    </PageContentLayout>
  );
};

export default WrittenReviewList;
