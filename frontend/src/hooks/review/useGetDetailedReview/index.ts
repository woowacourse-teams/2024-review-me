import { useSuspenseQuery } from '@tanstack/react-query';

import { getDetailedReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { DetailReviewData } from '@/types';

interface UseGetDetailedReviewProps {
  reviewId: number;
}

interface FetchDetailedReviewParams {
  reviewId: number;
}

const useGetDetailedReview = ({ reviewId }: UseGetDetailedReviewProps) => {
  const fetchDetailedReview = async ({ reviewId }: FetchDetailedReviewParams) => {
    const result = await getDetailedReviewApi({ reviewId });
    return result;
  };

  const result = useSuspenseQuery<DetailReviewData>({
    queryKey: [REVIEW_QUERY_KEY.detailedReview, reviewId],
    queryFn: () => fetchDetailedReview({ reviewId }),
    staleTime: 60 * 60 * 1000,
  });

  return result;
};

export default useGetDetailedReview;
