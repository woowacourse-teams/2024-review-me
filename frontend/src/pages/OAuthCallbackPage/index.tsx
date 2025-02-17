import { useEffect } from 'react';
import { useNavigate } from 'react-router';

import { StateType } from '@/components/login/GitHubLoginButton';
import { useToastContext } from '@/components/toast/ToastProvider';
import { ROUTE } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';
import { useOAuthLogin } from '@/hooks/oAuth';

import LoadingBar from '../LoadingPage/components/LoadingBar';

const OAuthCallbackPage = () => {
  const { queryString: gitHubAuthCode } = useSearchParamAndQuery({
    queryStringKey: 'code',
  });

  const navigate = useNavigate();
  const mutation = useOAuthLogin();

  // 쿼리 파라미터로 전달된 state 파싱
  const params = new URLSearchParams(location.search);
  const stateParam = params.get('state');
  const parsedState: StateType = JSON.parse(decodeURIComponent(stateParam!));
  const { prevUrl, action } = parsedState;

  // 연결 페이지에서 로그인 하는 경우 이전 동작을 담아 리다이렉트
  const redirectUrl =
    prevUrl && prevUrl.includes('review-zone') ? `${prevUrl}?login_action=${action}` : `/${ROUTE.reviewLinks}`;

  const { showToast } = useToastContext();

  useEffect(() => {
    if (gitHubAuthCode) {
      mutation.mutate(
        { gitHubAuthCode },
        {
          onSuccess: () => {
            navigate(redirectUrl, { replace: true });
            showToast({ type: 'success', message: '환영합니다! 첫 리뷰를 받아보세요!' });
          },
          onError: () => {
            navigate(prevUrl, { replace: true });
            showToast({ type: 'error', message: '로그인에 실패했어요. 다시 시도해주세요!' });
          },
        },
      );
    }
  }, []);

  return <LoadingBar />;
};

export default OAuthCallbackPage;
