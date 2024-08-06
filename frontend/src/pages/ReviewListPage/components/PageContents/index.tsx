import { useNavigate } from 'react-router';

import ReviewCard from '@/components/ReviewCard';
import { useGetReviewList } from '@/hooks';
import LoadingPage from '@/pages/LoadingPage';

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
  const { data, isLoading, error } = useGetReviewList(groupAccessCode);

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}?memberId=${MEMBER_ID}`);
  };

  return (
    <>
      <S.Layout>
        <ReviewInfoSection
          projectName={data.projectName}
          revieweeName={`${data.revieweeName}님에게 달린 리뷰입니다!`}
        />
        <SearchSection handleChange={() => {}} options={OPTIONS} placeholder={USER_SEARCH_PLACE_HOLDER} />
        <S.ReviewSection>
          {isLoading && <LoadingPage />}
          {error && <p>{error.message}</p>}
          <S.ReviewSection>
            {isLoading && <LoadingPage />}
            {error && <p>{error.message}</p>}
            {data &&
              data.reviews.map((review) => (
                // const isLastElement = pageIndex === data.pages.length - 1 && reviewIndex === page.reviews.length - 1;
                <div key={review.id} onClick={() => handleReviewClick(review.id)}>
                  <ReviewCard
                    id={review.id}
                    projectName={data.projectName}
                    createdAt={review.createdAt}
                    contentPreview={review.contentPreview}
                    keywords={review.keywords}
                  />
                  {/* <div ref={isLastElement ? lastReviewElementRef : null}></div> */}
                </div>
              ))}
          </S.ReviewSection>
        </S.ReviewSection>
      </S.Layout>
    </>
  );
};

export default PageContents;
