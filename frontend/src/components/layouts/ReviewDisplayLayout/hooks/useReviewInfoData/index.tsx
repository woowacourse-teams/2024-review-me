import { useSuspenseQuery } from '@tanstack/react-query';

import { getReviewSummaryInfoDataApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { ReviewInfoData } from '@/types';

interface UseReviewInfoDataProps {
  reviewRequestCode: string;
}
const useReviewInfoData = ({ reviewRequestCode }: UseReviewInfoDataProps) => {
  const fetchReviewInfoData = async () => {
    return await getReviewSummaryInfoDataApi(reviewRequestCode);
  };

  const { data } = useSuspenseQuery<ReviewInfoData>({
    queryKey: [REVIEW_QUERY_KEY.reviewInfoData, reviewRequestCode],
    queryFn: () => fetchReviewInfoData(),
    staleTime: 60 * 60 * 1000,
  });

  return data;
};

export default useReviewInfoData;
