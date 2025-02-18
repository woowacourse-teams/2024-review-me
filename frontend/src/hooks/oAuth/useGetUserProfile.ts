import { useQuery } from '@tanstack/react-query';

import { getUserProfileApi } from '@/apis/oAuth';
import { OAUTH_QUERY_KEY } from '@/constants';

const useGetUserProfile = () => {
  const { data, isFetching } = useQuery({
    queryKey: [OAUTH_QUERY_KEY.userProfile],
    queryFn: getUserProfileApi,
    staleTime: 24 * 60 * 60 * 1000,
  });

  return {
    userProfile: data,
    isUserLoggedIn: !!data,
    isFetching,
  };
};

export default useGetUserProfile;
