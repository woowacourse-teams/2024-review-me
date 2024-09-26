import { useNavigate } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import ReviewCard from '@/components/ReviewCard';
import { ROUTE } from '@/constants/route';
import { useGetReviewList, useSearchParamAndQuery } from '@/hooks';

import { useInfiniteScroll } from '../../hooks';
import ReviewEmptySection from '../ReviewEmptySection';
import ReviewInfoSection from '../ReviewInfoSection';

import * as S from './styles';

const PageContents = () => {
  const navigate = useNavigate();

  const { data, fetchNextPage, hasNextPage, isLoading, isSuccess } = useGetReviewList();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const lastReviewElementRef = useInfiniteScroll({
    fetchNextPage,
    hasNextPage,
    isLoading,
    isLastPage: data.pages[0].isLastPage,
  });

  const handleReviewClick = (id: number) => {
    navigate(`/${ROUTE.detailedReview}/${reviewRequestCode}/${id}`);
  };

  const { projectName, revieweeName } = data.pages[0];
  const reviews = data.pages.flatMap((page) => page.reviews);

  return (
    isSuccess && (
      <S.Layout>
        <ReviewInfoSection projectName={projectName} revieweeName={revieweeName} />
        {reviews.length === 0 ? (
          <ReviewEmptySection />
        ) : (
          <S.ReviewSection>
            {reviews.map((review, index) => {
              const isLastReview = reviews.length === index + 1;
              return (
                <UndraggableWrapper key={review.reviewId}>
                  <ReviewCard
                    projectName={projectName}
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
      </S.Layout>
    )
  );
};

export default PageContents;
