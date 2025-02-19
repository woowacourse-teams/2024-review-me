import { useQueryClient } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useNavigate } from 'react-router';

import { LoginActionContextType } from '@/components/login/GitHubLoginButton';
import { OAUTH_QUERY_KEY, ROUTE } from '@/constants';
import { useSearchParamAndQuery, useToastContext } from '@/hooks';
import { useOAuthLogin } from '@/hooks/oAuth';

import LoadingPage from '../LoadingPage';

const OAuthCallbackPage = () => {
  const { queryString: gitHubAuthCode } = useSearchParamAndQuery({
    queryStringKey: 'code',
  });

  const navigate = useNavigate();
  const { showToast } = useToastContext();

  const queryClient = useQueryClient();

  const mutation = useOAuthLogin();

  useEffect(() => {
    if (!gitHubAuthCode) return;

    // 쿼리 파라미터로 전달된 state 파싱
    const params = new URLSearchParams(location.search);
    const stateParam = params.get('state');
    if (!stateParam) return;

    const parsedState: LoginActionContextType = JSON.parse(decodeURIComponent(stateParam));
    const { prevUrl, action } = parsedState;

    // 연결 페이지에서 로그인 하는 경우 이전 동작을 담아 리다이렉트
    const redirectUrl =
      prevUrl && prevUrl.includes('review-zone') ? `${prevUrl}?login_action=${action}` : `/${ROUTE.reviewLinks}`;

    mutation.mutate(
      { gitHubAuthCode },
      {
        onSuccess: () => {
          navigate(redirectUrl, { replace: true });
          showToast({ type: 'success', message: '로그인 성공! 환영해요!', position: 'top' });

          // 로그인 성공 시 프로필 정보를 받아오도록 함
          queryClient.invalidateQueries({ queryKey: [OAUTH_QUERY_KEY.userProfile] });
        },
        onError: () => {
          navigate(prevUrl, { replace: true });
          showToast({ type: 'error', message: '로그인에 실패했어요. 다시 시도해주세요!', position: 'top' });
        },
      },
    );
  }, []);

  return <LoadingPage />;
};

export default OAuthCallbackPage;
