import { useNavigate } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import ReviewCard from '@/components/ReviewCard';
import { ROUTE } from '@/constants/route';
import { useGetReviewList, useSearchParamAndQuery } from '@/hooks';

import ReviewEmptySection from '../ReviewEmptySection';
import ReviewInfoSection from '../ReviewInfoSection';

import * as S from './styles';

const PageContents = () => {
  const navigate = useNavigate();

  const { data: reviewListData, isSuccess } = useGetReviewList();

  const { param: reviewRequestCode } = useSearchParamAndQuery({
    paramKey: 'reviewRequestCode',
  });

  const handleReviewClick = (id: number) => {
    navigate(`/${ROUTE.detailedReview}/${reviewRequestCode}/${id}`);
  };

  return (
    <>
      {isSuccess && (
        <S.Layout>
          <ReviewInfoSection projectName={reviewListData.projectName} revieweeName={reviewListData.revieweeName} />
          {reviewListData.reviews.length === 0 ? (
            <ReviewEmptySection />
          ) : (
            <S.ReviewSection>
              {reviewListData.reviews.map((review) => (
                <UndraggableWrapper key={review.reviewId}>
                  <div onClick={() => handleReviewClick(review.reviewId)}>
                    <ReviewCard
                      projectName={reviewListData.projectName}
                      createdAt={review.createdAt}
                      contentPreview={review.contentPreview}
                      categories={review.categories}
                    />
                  </div>
                </UndraggableWrapper>
              ))}
            </S.ReviewSection>
          )}
        </S.Layout>
      )}
    </>
  );
};

export default PageContents;
