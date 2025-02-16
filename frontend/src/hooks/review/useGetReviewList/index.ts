import { useSuspenseInfiniteQuery } from '@tanstack/react-query';

import { getReceivedReviewListApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';

interface UseGetReviewListProps {
  reviewRequestCode: string;
}
const useGetReviewList = ({ reviewRequestCode }: UseGetReviewListProps) => {
  const result = useSuspenseInfiniteQuery({
    queryKey: [REVIEW_QUERY_KEY.reviews],
    queryFn: ({ pageParam }) =>
      getReceivedReviewListApi({
        lastReviewId: pageParam === 0 ? null : pageParam, // 첫 api 요청 시, null 값 보내기
        size: 10,
        reviewRequestCode,
      }),

    initialPageParam: 0,
    getNextPageParam: (data) => {
      return data.lastReviewId;
    },
    staleTime: 1 * 60 * 1000,
  });

  return { ...result };
};

export default useGetReviewList;
