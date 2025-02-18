import { ROUTE_PARAM } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';

/**
 * 리뷰 상세보기 페이지에서 사용하는 reviewId의 출처를 정하는 훅
 * props로 받은 id가 있다면 그것을 사용하고 없다면 url에서 추출
 */
const useReviewId = (selectedReviewId?: number) => {
  const { param: reviewIdFromUrl } = useSearchParamAndQuery({
    paramKey: ROUTE_PARAM.reviewId,
  });

  return selectedReviewId || Number(reviewIdFromUrl);
};

export default useReviewId;
