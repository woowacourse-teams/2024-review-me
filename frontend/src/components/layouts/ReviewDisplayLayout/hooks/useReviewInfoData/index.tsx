import { useSuspenseQuery } from '@tanstack/react-query';

import { getReviewSummaryInfoDataApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { useReviewRequestCodeParam } from '@/hooks';
import { ReviewInfoData } from '@/types';

const useReviewInfoData = () => {
  const { reviewRequestCode } = useReviewRequestCodeParam();
  const fetchReviewInfoData = async () => {
    return await getReviewSummaryInfoDataApi(reviewRequestCode);
  };

  const { data } = useSuspenseQuery<ReviewInfoData>({
    queryKey: [REVIEW_QUERY_KEY.reviewInfoData],
    queryFn: () => fetchReviewInfoData(),
    staleTime: 60 * 60 * 1000,
  });

  return data;
};

export default useReviewInfoData;
