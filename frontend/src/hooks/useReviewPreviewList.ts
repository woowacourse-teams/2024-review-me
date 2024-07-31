import { useInfiniteQuery } from '@tanstack/react-query';

import { getReviewListApi } from '@/apis/review';
import { QUERY_KEYS } from '@/constants/queryKeys';

const MEMBER_ID = 2;

export const useReviewPreviewList = () => {
  const { data, fetchNextPage, hasNextPage, isLoading, error } = useInfiniteQuery({
    queryKey: [QUERY_KEYS.reviews],
    queryFn: ({ pageParam = 0 }) =>
      getReviewListApi({
        revieweeId: 1,
        lastReviewId: pageParam,
        memberId: MEMBER_ID,
      }),
    initialPageParam: 0,
    getNextPageParam: (data) => {
      if (data.lastReviewId) return data.lastReviewId;
      return null;
    },
  });

  return {
    data,
    fetchNextPage,
    hasNextPage,
    isLoading,
    error,
  };
};
