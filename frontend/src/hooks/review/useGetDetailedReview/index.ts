import { useSuspenseQuery } from '@tanstack/react-query';

import { getDetailedReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { DetailReviewData } from '@/types';

interface UseGetDetailedReviewProps {
  reviewId: number;
  groupAccessCode: string;
  reviewRequestCode: string;
}

interface FetchDetailedReviewParams {
  reviewId: number;
  groupAccessCode: string;
  reviewRequestCode: string;
}

const useGetDetailedReview = ({ reviewId, groupAccessCode, reviewRequestCode }: UseGetDetailedReviewProps) => {
  const fetchDetailedReview = async ({ reviewId, groupAccessCode, reviewRequestCode }: FetchDetailedReviewParams) => {
    const result = await getDetailedReviewApi({ reviewId, groupAccessCode, reviewRequestCode });
    return result;
  };

  const result = useSuspenseQuery<DetailReviewData>({
    queryKey: [REVIEW_QUERY_KEY.detailedReview, reviewId],
    queryFn: () => fetchDetailedReview({ reviewId, groupAccessCode, reviewRequestCode }),
    staleTime: 60 * 60 * 1000,
  });

  return result;
};

export default useGetDetailedReview;
