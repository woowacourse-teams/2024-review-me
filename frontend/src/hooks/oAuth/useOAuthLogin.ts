import { useMutation } from '@tanstack/react-query';

import { postOAuthLoginApi } from '@/apis/oAuth';
import { OAUTH_QUERY_KEY } from '@/constants';

interface UseOAuthLoginProps {
  gitHubAuthCode: string;
}

const useOAuthLogin = () => {
  const mutation = useMutation({
    mutationKey: [OAUTH_QUERY_KEY.gitHubLogin],
    mutationFn: async ({ gitHubAuthCode }: UseOAuthLoginProps) => {
      return await postOAuthLoginApi({ gitHubAuthCode });
    },
  });

  return mutation;
};

export default useOAuthLogin;
