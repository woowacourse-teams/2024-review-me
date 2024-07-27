import { useSuspenseQuery } from '@tanstack/react-query';
import { useLocation, useParams } from 'react-router';

import { DETAILED_REVIEW_API_PARAMS } from '@/apis/endpoints';
import { getDetailedReviewApi } from '@/apis/review';
import { RevieweeComments } from '@/components';
import { REVIEW_QUERY_KEYS } from '@/constants';
import { ReviewDescription, ReviewSection, KeywordSection } from '@/pages/DetailedReviewPage/components';
import { DetailReviewData } from '@/types';

import * as S from './styles';

const DetailedReviewPage = () => {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const memberId = queryParams.get(DETAILED_REVIEW_API_PARAMS.queryString.memberId);

  const fetchDetailedReview = async (reviewId: number, memberId: number) => {
    const result = await getDetailedReviewApi({ reviewId, memberId });
    return result;
  };

  const { data: detailedReview } = useSuspenseQuery<DetailReviewData>({
    queryKey: [REVIEW_QUERY_KEYS.detailedReview, id, memberId],
    queryFn: () => fetchDetailedReview(Number(id), Number(memberId)),
  });

  if (!detailedReview) throw new Error(' 상세보기 리뷰 데이터를 가져올 수 없어요.');
  // TODO: 리뷰 공개/비공개 토글 버튼 기능
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
