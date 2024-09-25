import { useNavigate } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import ReviewCard from '@/components/ReviewCard';
import { useGetReviewList } from '@/hooks';

import { useInfiniteScroll } from '../../hooks';
import ReviewEmptySection from '../ReviewEmptySection';
import ReviewInfoSection from '../ReviewInfoSection';
// import SearchSection from '../SearchSection';

import * as S from './styles';

// const USER_SEARCH_PLACE_HOLDER = '레포지토리명을 검색하세요';
// const OPTIONS = ['최신순', '오래된순'];

interface PageContentsProps {
  groupAccessCode: string;
  reviewRequestCode: string;
}

const PageContents = ({ groupAccessCode, reviewRequestCode }: PageContentsProps) => {
  const navigate = useNavigate();

  const { data, fetchNextPage, hasNextPage, isLoading, isSuccess } = useGetReviewList(
    groupAccessCode,
    reviewRequestCode,
  );

  const lastReviewElementRef = useInfiniteScroll({ fetchNextPage, hasNextPage, isLoading });

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}`);
  };

  const projectName = data.pages[0].projectName;
  const revieweeName = data.pages[0].revieweeName;
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
                    onClick={() => handleReviewClick(review.reviewId)}
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
