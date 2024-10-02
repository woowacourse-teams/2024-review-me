import { useSuspenseInfiniteQuery } from '@tanstack/react-query';

import { getReviewListApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';

const useGetReviewList = () => {
  const result = useSuspenseInfiniteQuery({
    queryKey: [REVIEW_QUERY_KEY.reviews],
    queryFn: ({ pageParam }) =>
      getReviewListApi({
        lastReviewId: pageParam === 0 ? null : pageParam, // 첫 api 요청 시, null 값 보내기
        size: 10,
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
