import { useNavigate } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import ReviewCard from '@/components/ReviewCard';
import { useGetReviewList } from '@/hooks';

import ReviewEmptySection from '../ReviewEmptySection';
import ReviewInfoSection from '../ReviewInfoSection';

import * as S from './styles';

// const USER_SEARCH_PLACE_HOLDER = '레포지토리명을 검색하세요';
// const OPTIONS = ['최신순', '오래된순'];

interface PageContentsProps {
  groupAccessCode: string;
  reviewRequestCode: string;
}

const PageContents = ({ groupAccessCode, reviewRequestCode }: PageContentsProps) => {
  const navigate = useNavigate();

  const { data: reviewListData, isSuccess } = useGetReviewList(groupAccessCode, reviewRequestCode);

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}`);
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
