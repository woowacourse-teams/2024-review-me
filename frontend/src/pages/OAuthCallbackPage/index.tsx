import { useEffect } from 'react';
import { useNavigate } from 'react-router';

import { useToastContext } from '@/components/toast/ToastProvider';
import { ROUTE } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';
import { useOAuthLogin } from '@/hooks/oAuth';

const OAuthCallbackPage = () => {
  const { queryString: gitHubAuthCode } = useSearchParamAndQuery({
    queryStringKey: 'code',
  });

  const navigate = useNavigate();
  const mutation = useOAuthLogin();

  const { showToast } = useToastContext();

  useEffect(() => {
    if (gitHubAuthCode) {
      mutation.mutate(
        { gitHubAuthCode },
        {
          onSuccess: () => {
            navigate(`/${ROUTE.reviewLinks}`, { replace: true });
            return showToast({ type: 'success', message: '환영합니다! 첫 리뷰를 받아보세요!' });
          },
          onError: () => {
            navigate(ROUTE.home, { replace: true });
            return showToast({ type: 'error', message: '로그인에 실패했어요. 다시 시도해주세요!' });
          },
        },
      );
    }
  }, []);

  return <h3>로그인 중...</h3>;
};

export default OAuthCallbackPage;
