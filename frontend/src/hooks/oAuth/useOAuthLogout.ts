import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postOAuthLogoutApi } from '@/apis/oAuth';
import { OAUTH_QUERY_KEY } from '@/constants';

import useToastContext from '../useToastContext';

const useOAuthLogout = () => {
  const queryClient = useQueryClient();
  const { showToast } = useToastContext();

  const mutation = useMutation({
    mutationKey: [OAUTH_QUERY_KEY.gitHubLogout],
    mutationFn: async () => {
      return await postOAuthLogoutApi();
    },

    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [OAUTH_QUERY_KEY.userProfile] });
      showToast({ type: 'success', message: '로그아웃이 완료되었어요!' });
    },
    onError: () => {
      showToast({ type: 'error', message: '로그아웃에 실패했어요. 다시 시도해주세요!' });
    },
  });

  return mutation;
};

export default useOAuthLogout;
