import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';

import { getReviewListApi } from '@/apis/review';
import ReviewPreviewCard from '@/components/ReviewPreviewCard';
import { ReviewPreview } from '@/types';

import SearchSection from './components/SearchSection';
import * as S from './styles';

const USER_SEARCH_PLACE_HOLDER = '레포지토리명을 검색하세요.';
const options = ['최신순', '오래된순'];

const mockReviewPreviews: ReviewPreview[] = [
  {
    id: 1,
    isPublic: true,
    reviewerGroup: {
      id: 1,
      name: '2024-review-me',
      thumbnailUrl: '',
    },
    createdAt: '2024-07-24',
    contentPreview: `물론 시중에 출간되어 있는 책들로 공부하는 것도 큰 장점이지만 더 깊은 공부를 하고 싶을 때 공식 문서를 확인해보는 것이 좋기 때문에, 저 개인적인 생각으로는 언어 공부를 아예 처음 입문하시는 분들은 한국에서 출간된 개발 서적으로 공부를 시작하시다가 모르는 부분이.....`,
    keywords: [
      { id: 1, content: '친절해요' },
      { id: 2, content: '호감이에요' },
      { id: 3, content: '잘 먹어요' },
    ],
  },
  {
    id: 2,
    isPublic: false,
    reviewerGroup: {
      id: 2,
      name: '2023-haru-study',
      thumbnailUrl: '',
    },
    createdAt: '2023-08-29',
    contentPreview: `하루스터디는 효율적인 공부 방법을 제공하는 학습 진행 도구 서비스입니다. 하루스터디는 목표 설정 단계, 학습 단계, 회고 단계 를 반복하는 학습 사이클을 통해 학습 효율을 끌어올립니다. 하루스터디를 사용하게 되면 '학습을 잘 하는 방법'에 대해서...

`,
    keywords: [
      { id: 4, content: '시간 약속을 잘 지켜요' },
      { id: 5, content: '열정 넘쳐요' },
    ],
  },
  {
    id: 3,
    isPublic: true,
    reviewerGroup: {
      id: 3,
      name: '2021-zzimkkong',
      thumbnailUrl: '',
    },
    createdAt: '2021-08-01',
    contentPreview: `공간을 한 눈에, 예약은 한 번에! 맞춤형 공간예약 서비스 제작 플랫폼 찜꽁입니다! 공간 제공자(관리자)는 에디터를 통해 공간을 생성할 수 있습니다! 생성한 공간은 링크를 통해 사용자에게 제공할 수 있으며, 사용자는 링크를 통해 간편하게 공간을 확인하고 예약을...`,
    keywords: [
      { id: 6, content: '업무에 헌신적이에요' },
      { id: 7, content: '협업에 능숙해요' },
    ],
  },
];

const ReviewPreviewListPage = () => {
  const [reviews, setReviews] = useState<ReviewPreview[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const data = await getReviewListApi({ revieweeId: 1, lastReviewId: 4, memberId: 4 });
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
    navigate(`/user/detailed-review/${id}?memberId=${4}`);
  };

  return (
    <>
      <S.Layout>
        <SearchSection onChange={() => {}} options={options} placeholder={USER_SEARCH_PLACE_HOLDER} />
        <S.ReviewSection>
          {mockReviewPreviews.map((item) => (
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
          {/* {loading && <p>로딩 중...</p>}
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
            ))} */}
        </S.ReviewSection>
      </S.Layout>
    </>
  );
};

export default ReviewPreviewListPage;
