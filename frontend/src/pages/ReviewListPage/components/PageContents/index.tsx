import { useNavigate } from 'react-router';

import ReviewCard from '@/components/ReviewCard';
import { useGetReviewList } from '@/hooks';

import ReviewInfoSection from '../ReviewInfoSection';
import SearchSection from '../SearchSection';

import * as S from './styles';

const USER_SEARCH_PLACE_HOLDER = '레포지토리명을 검색하세요.';
const OPTIONS = ['최신순', '오래된순'];
const MEMBER_ID = 2;

interface PageContentsProps {
  groupAccessCode: string;
}

const PageContents = ({ groupAccessCode }: PageContentsProps) => {
  const navigate = useNavigate();
  const { data: ReviewListData } = useGetReviewList(groupAccessCode);

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}?memberId=${MEMBER_ID}`);
  };

  return (
    <>
      <S.Layout>
        <ReviewInfoSection projectName={ReviewListData.projectName} revieweeName={ReviewListData.revieweeName} />
        {/* <SearchSection handleChange={() => {}} options={OPTIONS} placeholder={USER_SEARCH_PLACE_HOLDER} /> */}
        <S.ReviewSection>
          {ReviewListData.reviews.map((review) => (
            // const isLastElement = pageIndex === data.pages.length - 1 && reviewIndex === page.reviews.length - 1;
            <div key={review.reviewId} onClick={() => handleReviewClick(review.reviewId)}>
              <ReviewCard
                projectName={ReviewListData.projectName}
                createdAt={review.createdAt}
                contentPreview={review.contentPreview}
                categories={review.categories}
              />
              {/* <div ref={isLastElement ? lastReviewElementRef : null}></div> */}
            </div>
          ))}
        </S.ReviewSection>
      </S.Layout>
    </>
  );
};

export default PageContents;
