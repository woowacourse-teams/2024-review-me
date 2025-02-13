import { useSuspenseInfiniteQuery } from '@tanstack/react-query';

import { getReviewListApi } from '@/apis/review';
import { REVIEW_QUERY_KEY, ROUTE_PARAM } from '@/constants';
import useSearchParamAndQuery from '@/hooks/useSearchParamAndQuery';

const useGetReviewList = () => {
  const { param } = useSearchParamAndQuery({ paramKey: ROUTE_PARAM.reviewRequestCode });
  if (!param) console.error('reviewRequestCode를 읽지 못했어요');

  const result = useSuspenseInfiniteQuery({
    queryKey: [REVIEW_QUERY_KEY.reviews],
    queryFn: ({ pageParam }) =>
      getReviewListApi({
        lastReviewId: pageParam === 0 ? null : pageParam, // 첫 api 요청 시, null 값 보내기
        size: 10,
        reviewRequestCode: param ?? '',
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
