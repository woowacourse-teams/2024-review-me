import { useEffect, useState } from 'react';
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
  const [reviews, setReviews] = useState<ReviewPreview[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const data = await getReviewListApi({ revieweeId: 1, lastReviewId: 5, memberId: MEMBER_ID });
        setReviews(data.reviews);
        setLoading(false);
      } catch (error) {
        setError('리뷰 리스트를 불러오는 데 실패했습니다.');
        setLoading(false);
      }
    };

    fetchReviews();
  }, []);

  const handleReviewClick = (id: number) => {
    navigate(`/user/detailed-review/${id}?memberId=${MEMBER_ID}`);
    // navigate(`/user/detailed-review/${123456}?memberId=${123456}`); // NOTE: MSW용 하드코딩
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
            reviews.map((item) => (
              <div key={item.id} onClick={() => handleReviewClick(item.id)}>
                <ReviewPreviewCard
                  key={item.id}
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
