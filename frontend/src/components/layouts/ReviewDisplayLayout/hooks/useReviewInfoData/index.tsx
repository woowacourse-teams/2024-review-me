import { useSuspenseQuery } from '@tanstack/react-query';

import { getReviewInfoDataApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { ReviewInfoData } from '@/types';

const useReviewInfoData = () => {
  const fetchReviewInfoData = async () => {
    return await getReviewInfoDataApi();
  };

  const { data } = useSuspenseQuery<ReviewInfoData>({
    queryKey: [REVIEW_QUERY_KEY.reviewInfoData],
    queryFn: () => fetchReviewInfoData(),
    staleTime: 60 * 60 * 1000,
  });

  return data;
};

export default useReviewInfoData;
