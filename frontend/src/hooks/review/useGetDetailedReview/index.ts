import { useSuspenseQuery } from '@tanstack/react-query';

import { getDetailedReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { DetailReviewData } from '@/types';

interface UseGetDetailedReviewProps {
  reviewId: number;
  groupAccessCode: string;
}

interface FetchDetailedReviewParams {
  reviewId: number;
  groupAccessCode: string;
}

const useGetDetailedReview = ({ reviewId, groupAccessCode }: UseGetDetailedReviewProps) => {
  const fetchDetailedReview = async ({ reviewId, groupAccessCode }: FetchDetailedReviewParams) => {
    const result = await getDetailedReviewApi({ reviewId, groupAccessCode });
    return result;
  };

  const result = useSuspenseQuery<DetailReviewData>({
    queryKey: [REVIEW_QUERY_KEY.detailedReview, reviewId],
    queryFn: () => fetchDetailedReview({ reviewId, groupAccessCode }),
  });

  return result;
};

export default useGetDetailedReview;
