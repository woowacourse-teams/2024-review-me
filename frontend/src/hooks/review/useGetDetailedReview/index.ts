import { useSuspenseQuery } from '@tanstack/react-query';

import { getDetailedReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEYS } from '@/constants';
import { DetailReviewData } from '@/types';

interface UseGetDetailedReviewProps {
  reviewId: number;
  memberId: number;
  groupAccessCode: string;
}

interface FetchDetailedReviewParams {
  reviewId: number;
  memberId: number;
  groupAccessCode: string;
}

const useGetDetailedReview = ({ reviewId, memberId, groupAccessCode }: UseGetDetailedReviewProps) => {
  const fetchDetailedReview = async ({ reviewId, memberId, groupAccessCode }: FetchDetailedReviewParams) => {
    const result = await getDetailedReviewApi({ reviewId, memberId, groupAccessCode });
    return result;
  };

  const result = useSuspenseQuery<DetailReviewData>({
    queryKey: [REVIEW_QUERY_KEYS.detailedReview, reviewId, memberId],
    queryFn: () => fetchDetailedReview({ reviewId, memberId, groupAccessCode }),
  });

  return result;
};

export default useGetDetailedReview;
