import { useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router';

import { getReviewListApi } from '@/apis/review';
import ReviewPreviewCard from '@/components/ReviewPreviewCard';
import { ReviewPreview } from '@/types';

import SearchSection from './components/SearchSection';
import * as S from './styles';

const USER_SEARCH_PLACE_HOLDER = '레포지토리명을 검색하세요.';
const OPTIONS = ['최신순', '오래된순'];
const MEMBER_ID = 2;

const ReviewPreviewListPage = () => {
  const navigate = useNavigate();

  const { data, error, isLoading } = useQuery<ReviewPreviewList>({
    queryKey: [QUERY_KEYS.reviews],
    queryFn: () => getReviewListApi({ revieweeId: 1, lastReviewId: 5, memberId: MEMBER_ID }),
  });

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}?memberId=${MEMBER_ID}`);
  };

  return (
    <>
      <S.Layout>
        <SearchSection onChange={() => {}} options={OPTIONS} placeholder={USER_SEARCH_PLACE_HOLDER} />
        <S.ReviewSection>
          {loading && <p>로딩 중...</p>}
          {error && <p>{error}</p>}
          {!loading &&
            !error &&
            data &&
            data.reviews.map((item: ReviewPreview) => (
              <div key={item.id} onClick={() => handleReviewClick(item.id)}>
                <ReviewPreviewCard
                  id={item.id}
                  reviewerGroup={item.reviewerGroup}
                  createdAt={item.createdAt}
                  contentPreview={item.contentPreview}
                  keywords={item.keywords}
                  isPublic={item.isPublic}
                />
              </div>
            ))}
        </S.ReviewSection>
      </S.Layout>
    </>
  );
};

export default ReviewPreviewListPage;
