import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postOAuthLogoutApi } from '@/apis/oAuth';
import { OAUTH_QUERY_KEY } from '@/constants';
//아아
const useOAuthLogout = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationKey: [OAUTH_QUERY_KEY.gitHubLogout],
    mutationFn: async () => {
      return await postOAuthLogoutApi();
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [OAUTH_QUERY_KEY.userProfile] });
    },
  });

  return mutation;
};

export default useOAuthLogout;
