// import { useInfiniteQuery, useSuspenseQuery } from '@tanstack/react-query';

// import { getReviewListApi } from '@/apis/review';
// import { QUERY_KEYS } from '@/constants/queryKeys';

export const GROUP_ACCESS_CODE = 'cJWJ22kv'; // NOTE: 임시 그룹코드

export const useReviewPreviewList = () => {
  // const { data, fetchNextPage, hasNextPage, isLoading, error } = useInfiniteQuery({
  //   queryKey: [QUERY_KEYS.reviews],
  //   queryFn: ({ pageParam }) => getReviewListApi(pageParam),
  //   initialPageParam: GROUP_ACCESS_CODE,
  //   getNextPageParam: (data) => {
  //     return data.lastReviewId ? data.lastReviewId : undefined; // 마지막 리뷰 ID가 없을 경우 undefined 반환
  //   },
  // });

  return {
    // data,
    // fetchNextPage,
    // hasNextPage,
    // isLoading,
    // error,
  };
};
