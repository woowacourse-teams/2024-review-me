import { useNavigate } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import ReviewDisplayLayout from '@/components/layouts/ReviewDisplayLayout';
import ReviewCard from '@/components/ReviewCard';
import { REVIEW_EMPTY } from '@/constants';
import { ROUTE } from '@/constants/route';
import { useGetReviewList, useSearchParamAndQuery } from '@/hooks';

import ReviewEmptySection from '../../../../components/common/ReviewEmptySection';
import { useInfiniteScroll } from '../../hooks';

import * as S from './styles';

const ReviewListPageContents = () => {
  const navigate = useNavigate();

  const { data, fetchNextPage, isLoading, isSuccess } = useGetReviewList();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

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
    <ReviewDisplayLayout isReviewList={true}>
      {reviews.length === 0 ? (
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
    </ReviewDisplayLayout>
  );
};

export default ReviewListPageContents;
