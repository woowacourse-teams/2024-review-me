import { useSuspenseQuery } from '@tanstack/react-query';

import { getReviewGroupDataApi } from '@/apis/group';
import { GROUP_QUERY_KEY } from '@/constants';
import { ReviewGroupData } from '@/types';

interface FetchReviewGroupDataParams {
  reviewRequestCode: string;
}

interface UseGetReviewGroupDataProps extends FetchReviewGroupDataParams {}

const useGetReviewGroupData = ({ reviewRequestCode }: UseGetReviewGroupDataProps) => {
  const fetchReviewGroupData = async ({ reviewRequestCode }: FetchReviewGroupDataParams) => {
    const result = await getReviewGroupDataApi(reviewRequestCode);

    return result;
  };

  const result = useSuspenseQuery<ReviewGroupData>({
    queryKey: [GROUP_QUERY_KEY.reviewGroupData, reviewRequestCode],
    queryFn: () => fetchReviewGroupData({ reviewRequestCode }),
    staleTime: 60 * 60 * 1000,
  });

  return result;
};

export default useGetReviewGroupData;
