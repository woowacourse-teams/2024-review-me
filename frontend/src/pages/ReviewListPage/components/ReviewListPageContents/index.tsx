import { useCallback, useContext } from 'react';
import { useNavigate } from 'react-router';

import { ReviewEmptySection, ReviewPreview } from '@/components';
import { ReviewInfoDataContext } from '@/components/layouts/ReviewDisplayLayout/ReviewInfoDataProvider';
import { REVIEW_EMPTY } from '@/constants';
import { ROUTE } from '@/constants/route';
import { useGetReviewList, useInfiniteScroll, useReviewRequestCodeParam } from '@/hooks';

import * as S from './styles';

const ReviewListPageContents = () => {
  const navigate = useNavigate();
  const { reviewRequestCode } = useReviewRequestCodeParam();
  const { isLastPage, reviewList, fetchNextPage, isFetchingNextPage, isSuccess } = useGetReviewList({
    reviewRequestCode,
  });
  const { totalReviewCount } = useContext(ReviewInfoDataContext);

  const handleReviewClick = useCallback(
    (id: number) => {
      navigate(`/${ROUTE.detailedReview}/${reviewRequestCode}/${id}`);
    },
    [reviewRequestCode],
  );

  const lastReviewElementRef = useInfiniteScroll({
    fetchNextPage,
    isFetchingNextPage,
    isLastPage,
  });

  if (!isSuccess) return null;

  return (
    <>
      {totalReviewCount === 0 ? (
        <ReviewEmptySection content={REVIEW_EMPTY.noReviewInTotal} />
      ) : (
        <S.ReviewSection>
          {reviewList.map((review) => (
            <ReviewPreview
              id={review.reviewId}
              key={review.reviewId}
              createdAt={review.createdAt}
              contentPreview={review.contentPreview}
              categories={review.categories}
              handleClick={handleReviewClick}
            />
          ))}
          {!isFetchingNextPage && !isLastPage && <div ref={lastReviewElementRef} style={{ height: '0.1rem' }} />}
        </S.ReviewSection>
      )}
    </>
  );
};

export default ReviewListPageContents;
