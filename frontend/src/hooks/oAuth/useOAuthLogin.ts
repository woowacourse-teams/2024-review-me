import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postOAuthLoginApi } from '@/apis/oAuth';
import { OAUTH_QUERY_KEY } from '@/constants';

interface UseOAuthLoginProps {
  gitHubAuthCode: string;
}

const useOAuthLogin = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationKey: [OAUTH_QUERY_KEY.gitHubLogin],
    mutationFn: async ({ gitHubAuthCode }: UseOAuthLoginProps) => {
      return await postOAuthLoginApi({ gitHubAuthCode });
    },
    onSuccess: () => {
      // 로그인 성공 시 프로필 정보를 받아오도록 함
      queryClient.invalidateQueries({ queryKey: [OAUTH_QUERY_KEY.userProfile] });
    },
  });

  return mutation;
};

export default useOAuthLogin;
