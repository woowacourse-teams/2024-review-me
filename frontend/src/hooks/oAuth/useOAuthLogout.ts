import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useLocation, useNavigate } from 'react-router';

import { postOAuthLogoutApi } from '@/apis/oAuth';
import { OAUTH_QUERY_KEY, ROUTE } from '@/constants';

import useToastContext from '../useToastContext';

const useOAuthLogout = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const queryClient = useQueryClient();
  const { showToast } = useToastContext();

  const redirectOnSuccess = () => {
    if (location.pathname.includes('review-zone')) return;
    return navigate(ROUTE.home, { replace: true });
  };

  const mutation = useMutation({
    mutationKey: [OAUTH_QUERY_KEY.gitHubLogout],
    mutationFn: async () => {
      return await postOAuthLogoutApi();
    },

    onSuccess: () => {
      showToast({ type: 'success', message: '로그아웃 완료!', position: 'top' });
      queryClient.clear();
      redirectOnSuccess();
    },
    onError: () => {
      showToast({ type: 'error', message: '로그아웃에 실패했어요. 다시 시도해주세요!', position: 'top' });
    },
  });

  return mutation;
};

export default useOAuthLogout;
