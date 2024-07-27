import { useQuery } from '@tanstack/react-query';
import { useLocation, useParams } from 'react-router';

import { getDetailedReviewApi } from '@/apis/review';
import { RevieweeComments } from '@/components';
import { DetailReviewData } from '@/types';

import KeywordSection from './components/KeywordSection';
import ReviewDescription from './components/ReviewDescription';
import ReviewSection from './components/ReviewSection/index';
import * as S from './styles';

const DetailedReviewPage = () => {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const memberId = queryParams.get('memberId');

  const fetchDetailedReview = async (reviewId: number, memberId: number) => {
    const result = await getDetailedReviewApi({ reviewId, memberId });
    return result;
  };

  const {
    data: detailedReview,
    isLoading,
    error,
  } = useQuery<DetailReviewData, Error>({
    queryKey: ['detailedReview', id, memberId],
    queryFn: () => fetchDetailedReview(Number(id), Number(memberId)),
    enabled: !!id && !!memberId,
  });

  if (isLoading) return <div>Loading...</div>;

  if (error) return <div>Error: {error.message}</div>;
  if (!detailedReview) return <div>Error: 상세보기 리뷰 데이터를 가져올 수 없어요.</div>;

  return (
    <S.DetailedReviewPage>
      <ReviewDescription
        projectName={detailedReview.reviewerGroup.name}
        date={new Date(detailedReview.createdAt)}
        isPublic={true}
        handleClickToggleButton={() => console.log('click toggle ')}
      />
      <RevieweeComments comment={detailedReview.reviewerGroup.description} />
      {detailedReview.reviews.map((item, index) => (
        <ReviewSection question={item.question} answer={item.answer} key={index} index={index} />
      ))}
      <KeywordSection keywords={detailedReview.keywords} index={detailedReview.reviews.length} />
    </S.DetailedReviewPage>
  );
};

export default DetailedReviewPage;
