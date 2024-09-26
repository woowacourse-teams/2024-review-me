import { useSuspenseInfiniteQuery } from '@tanstack/react-query';

import { getReviewListApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';

const useGetReviewList = (groupAccessCode: string, reviewRequestCode: string) => {
  const { data, fetchNextPage, hasNextPage, isLoading, isSuccess } = useSuspenseInfiniteQuery({
    queryKey: [REVIEW_QUERY_KEY.reviews],
    queryFn: ({ pageParam }) =>
      getReviewListApi({
        groupAccessCode,
        reviewRequestCode,
        lastReviewId: pageParam === 0 ? null : pageParam, // 첫 api 요청 시, null 값 보내기
        size: pageParam === 0 ? 10 : 5, // 첫 api 요청 시, 10개의 리뷰를 불러오고 그 이후로는 5개씩 불러온다.
      }),

    initialPageParam: 0,
    getNextPageParam: (data) => {
      return data.lastReviewId;
    },
    staleTime: 1 * 60 * 1000,
  });

  return {
    data,
    fetchNextPage,
    hasNextPage,
    isLoading,
    isSuccess,
  };
};

export default useGetReviewList;
