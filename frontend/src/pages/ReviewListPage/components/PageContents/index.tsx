import { useNavigate } from 'react-router';

import ReviewCard from '@/components/ReviewCard';
import { useGetReviewList } from '@/hooks';

import ReviewEmptySection from '../ReviewEmptySection';
import ReviewInfoSection from '../ReviewInfoSection';
// import SearchSection from '../SearchSection';

import * as S from './styles';

// const USER_SEARCH_PLACE_HOLDER = '레포지토리명을 검색하세요.';
// const OPTIONS = ['최신순', '오래된순'];

interface PageContentsProps {
  groupAccessCode: string;
}

const PageContents = ({ groupAccessCode }: PageContentsProps) => {
  const navigate = useNavigate();

  const { data: reviewListData } = useGetReviewList(groupAccessCode);

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}`);
  };

  return (
    <S.Layout>
      <ReviewInfoSection projectName={reviewListData.projectName} revieweeName={reviewListData.revieweeName} />
      {reviewListData.reviews.length === 0 && <ReviewEmptySection />}
      {/* <SearchSection handleChange={() => {}} options={OPTIONS} placeholder={USER_SEARCH_PLACE_HOLDER} /> */}
      <S.ReviewSection>
        {reviewListData.reviews.map((review) => (
          // const isLastElement = pageIndex === data.pages.length - 1 && reviewIndex === page.reviews.length - 1;
          <div key={review.reviewId} onClick={() => handleReviewClick(review.reviewId)}>
            <ReviewCard
              projectName={reviewListData.projectName}
              createdAt={review.createdAt}
              contentPreview={review.contentPreview}
              categories={review.categories}
            />
            {/* <div ref={isLastElement ? lastReviewElementRef : null}></div> */}
          </div>
        ))}
      </S.ReviewSection>
    </S.Layout>
  );
};

export default PageContents;
