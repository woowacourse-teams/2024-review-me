import { useSuspenseQuery } from '@tanstack/react-query';

import { getDetailedReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { DetailReviewData } from '@/types';

interface UseGetDetailedReviewProps {
  reviewId: number;
}

const useGetDetailedReview = ({ reviewId }: UseGetDetailedReviewProps) => {
  const result = useSuspenseQuery<DetailReviewData>({
    queryKey: [REVIEW_QUERY_KEY.detailedReview, reviewId],
    queryFn: () => getDetailedReviewApi({ reviewId }),
    staleTime: 60 * 60 * 1000,
  });

  return result;
};

export default useGetDetailedReview;
