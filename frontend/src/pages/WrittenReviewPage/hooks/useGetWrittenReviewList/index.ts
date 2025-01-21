import { useSuspenseInfiniteQuery } from '@tanstack/react-query';

import { getWrittenReviewList } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';

const useGetWrittenReviewList = () => {
  const result = useSuspenseInfiniteQuery({
    queryKey: [REVIEW_QUERY_KEY.writtenReviewList],
    queryFn: ({ pageParam }) =>
      getWrittenReviewList({
        lastReviewId: pageParam === 0 ? null : pageParam, // 첫 요청일 때 null으로 보냄
        size: 10,
      }),

    initialPageParam: 0,
    getNextPageParam: (data) => data.lastReviewId,

    staleTime: 1 * 60 * 1000,
  });

  return { ...result };
};

export default useGetWrittenReviewList;
