import { useSuspenseQuery } from '@tanstack/react-query';

import { getReviewLinksApi } from '@/apis/review';
import { REVIEW_QUERY_KEY } from '@/constants';
import { useGetUserProfile } from '@/hooks/oAuth';

const useGetReviewLinks = () => {
  const { userProfile, isUserLoggedIn } = useGetUserProfile();

  const makeQueryKey = () => {
    if (isUserLoggedIn && userProfile) {
      return [REVIEW_QUERY_KEY.reviewLinks, userProfile.memberId];
    }
    return [REVIEW_QUERY_KEY.reviewLinks];
  };

  const result = useSuspenseQuery({
    queryKey: makeQueryKey(),
    queryFn: () => getReviewLinksApi(),
    staleTime: 60 * 60 * 1000,
  });

  return result;
};

export default useGetReviewLinks;
