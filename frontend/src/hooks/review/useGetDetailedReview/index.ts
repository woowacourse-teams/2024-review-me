import { useSuspenseQuery } from '@tanstack/react-query';

import { getDetailedReviewApi } from '@/apis/review';
import { REVIEW_QUERY_KEYS } from '@/constants';
import { DetailReviewData } from '@/types';

interface UseGetDetailedReviewProps {
  id: number;
  memberId: number;
  groupAccessCode: string;
}

const useGetDetailedReview = ({ id, memberId, groupAccessCode }: UseGetDetailedReviewProps) => {
  const fetchDetailedReview = async (reviewId: number, memberId: number, groupAccessCode: string) => {
    const result = await getDetailedReviewApi({ reviewId, memberId, groupAccessCode });
    return result;
  };

  const { data: detailedReview, error } = useSuspenseQuery<DetailReviewData>({
    queryKey: [REVIEW_QUERY_KEYS.detailedReview, id, memberId],
    queryFn: () => fetchDetailedReview(id, memberId, groupAccessCode),
  });

  return {
    detailedReview,
    error,
  };
};

export default useGetDetailedReview;
