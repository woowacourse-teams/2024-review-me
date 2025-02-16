import { useContext } from 'react';
import { useNavigate } from 'react-router';

import { ReviewEmptySection } from '@/components';
import ReviewCard from '@/components/common/ReviewCard';
import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { ReviewInfoDataContext } from '@/components/layouts/ReviewDisplayLayout/ReviewInfoDataProvider';
import { REVIEW_EMPTY } from '@/constants';
import { ROUTE } from '@/constants/route';
import { useGetReviewList, useReviewRequestCodeParam } from '@/hooks';

import { useInfiniteScroll } from '../../hooks';

import * as S from './styles';

const ReviewListPageContents = () => {
  const navigate = useNavigate();
  const { reviewRequestCode } = useReviewRequestCodeParam();

  const { data, fetchNextPage, isLoading, isSuccess } = useGetReviewList({ reviewRequestCode });

  const { totalReviewCount } = useContext(ReviewInfoDataContext);

  const handleReviewClick = (id: number) => {
    navigate(`/${ROUTE.detailedReview}/${reviewRequestCode}/${id}`);
  };

  const isLastPage = data.pages[data.pages.length - 1].isLastPage;
  const reviews = data.pages.flatMap((page) => page.reviews);

  const lastReviewElementRef = useInfiniteScroll({
    fetchNextPage,
    isLoading,
    isLastPage,
  });

  if (!isSuccess) return null;

  return (
    <>
      {totalReviewCount === 0 ? (
        <ReviewEmptySection content={REVIEW_EMPTY.noReviewInTotal} />
      ) : (
        <S.ReviewSection>
          {reviews.map((review, index) => {
            const isLastReview = reviews.length === index + 1;
            return (
              <UndraggableWrapper key={review.reviewId}>
                <ReviewCard
                  createdAt={review.createdAt}
                  contentPreview={review.contentPreview}
                  categories={review.categories}
                  handleClick={() => handleReviewClick(review.reviewId)}
                />
                <div ref={isLastReview ? lastReviewElementRef : null} style={{ height: '0.1rem' }} />
              </UndraggableWrapper>
            );
          })}
        </S.ReviewSection>
      )}
    </>
  );
};

export default ReviewListPageContents;
