import { useSuspenseInfiniteQuery } from '@tanstack/react-query';

import { getReceivedReviewListApi } from '@/apis/review';
import { DEFAULT_SIZE_PER_PAGE, REVIEW_QUERY_KEY } from '@/constants';

interface UseGetReviewListProps {
  reviewRequestCode: string;
}
const useGetReviewList = ({ reviewRequestCode }: UseGetReviewListProps) => {
  const { data, ...rest } = useSuspenseInfiniteQuery({
    queryKey: [REVIEW_QUERY_KEY.reviews, reviewRequestCode],
    queryFn: ({ pageParam }) =>
      getReceivedReviewListApi({
        lastReviewId: pageParam === 0 ? null : pageParam, // 첫 api 요청 시, null 값 보내기
        size: DEFAULT_SIZE_PER_PAGE,
        reviewRequestCode,
      }),

    initialPageParam: 0,
    getNextPageParam: (lastPage) => lastPage.lastReviewId,

    staleTime: 1 * 60 * 1000,
  });

  const isLastPage = data.pages[data.pages.length - 1].isLastPage;
  const reviewList = data.pages.flatMap((page) => page.reviews) || [];

  return { isLastPage, reviewList, ...rest };
};

export default useGetReviewList;
