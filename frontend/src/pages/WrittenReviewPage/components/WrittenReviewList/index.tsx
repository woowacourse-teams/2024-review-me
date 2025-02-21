import { useCallback, useRef } from 'react';
import { useNavigate } from 'react-router';

import { ReviewPreview, TopButton } from '@/components';
import { useInfiniteScroll } from '@/hooks';

import { useDeviceBreakpoints, useGetWrittenReviewList } from '../../hooks';
import { PageContentLayout } from '../layouts';

import * as S from './styles';

const WrittenReviewList = () => {
  const navigate = useNavigate();
  const { deviceType } = useDeviceBreakpoints();
  const { reviewList, isLastPage, fetchNextPage, isSuccess, isFetchingNextPage } = useGetWrittenReviewList();

  const containerRef = useRef<HTMLUListElement | null>(null);

  const lastReviewElementRef = useInfiniteScroll({
    fetchNextPage,
    isFetchingNextPage,
    isLastPage,
  });

  const handleReviewItemClick = useCallback((reviewId: number) => {
    const params = new URLSearchParams();
    params.set('reviewId', reviewId.toString());

    navigate(`${location.pathname}?${params.toString()}`);
  }, []);

  return (
    <PageContentLayout title="작성한 리뷰 목록">
      {isSuccess && (
        <S.WrittenReviewList ref={containerRef}>
          {reviewList.map((review) => (
            <ReviewPreview
              id={review.reviewId}
              key={review.reviewId}
              createdAt={review.createdAt}
              contentPreview={review.contentPreview}
              categoryOptions={review.categoryOptions}
              projectName={review.projectName}
              revieweeName={review.revieweeName}
              handleClick={handleReviewItemClick}
            />
          ))}
          {!isFetchingNextPage && !isLastPage && (
            <div ref={lastReviewElementRef} style={{ minWidth: '0.1rem', minHeight: '0.1rem' }} />
          )}

          {!deviceType.isDesktop && <TopButton containerRef={containerRef} />}
        </S.WrittenReviewList>
      )}
    </PageContentLayout>
  );
};

export default WrittenReviewList;
