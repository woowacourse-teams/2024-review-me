import { useSuspenseQuery } from '@tanstack/react-query';

import { getReviewListApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';

const useGetReviewList = (groupAccessCode: string) => {
  const { data, isLoading, error, isSuccess } = useSuspenseQuery({
    queryKey: [REVIEW_QUERY_KEY.reviews],
    queryFn: () => getReviewListApi(groupAccessCode),
  });

  // NOTE: 무한스크롤 관련 코드 일단 주석 처리
  // const { data, fetchNextPage, hasNextPage, isLoading, error } = useInfiniteQuery({
  //   queryKey: [REVIEW_QUERY_KEY.reviews],
  //   queryFn: ({ pageParam }) => getReviewListApi(pageParam),
  //   initialPageParam: GROUP_ACCESS_CODE,
  //   getNextPageParam: (data) => {
  //     return data.lastReviewId ? data.lastReviewId : undefined; // 마지막 리뷰 ID가 없을 경우 undefined 반환
  //   },
  // });

  return {
    data,
    isLoading,
    error,
    isSuccess,
    // fetchNextPage,
    // hasNextPage,
  };
};

export default useGetReviewList;
