import { useSuspenseInfiniteQuery } from '@tanstack/react-query';

import { getWrittenReviewListApi } from '@/apis/review';
import { DEFAULT_SIZE_PER_PAGE, REVIEW_QUERY_KEY } from '@/constants';
import { useGetUserProfile } from '@/hooks/oAuth';

const useGetWrittenReviewList = () => {
  const { userProfile, isUserLoggedIn } = useGetUserProfile();

  const { data, ...rest } = useSuspenseInfiniteQuery({
    queryKey: [REVIEW_QUERY_KEY.writtenReviewList, isUserLoggedIn && userProfile && userProfile.memberId],
    queryFn: ({ pageParam }) =>
      getWrittenReviewListApi({
        lastReviewId: pageParam === 0 ? null : pageParam, // 첫 요청일 때 null으로 보냄
        size: DEFAULT_SIZE_PER_PAGE,
      }),

    initialPageParam: 0,
    getNextPageParam: (lastPage) => lastPage.lastReviewId,

    staleTime: 1 * 60 * 1000,
  });

  const isLastPage = data.pages[data.pages.length - 1].isLastPage;
  const reviewList = data.pages.flatMap((page) => page.reviews) || [];

  return { reviewList, isLastPage, ...rest };
};

export default useGetWrittenReviewList;
