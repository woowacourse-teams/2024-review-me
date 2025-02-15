import { useEffect } from 'react';
import { useNavigate } from 'react-router';

import { ROUTE } from '@/constants';
import { useSearchParamAndQuery } from '@/hooks';
import { useOAuthLogin } from '@/hooks/oAuth';

const OAuthCallbackPage = () => {
  const { queryString: gitHubAuthCode } = useSearchParamAndQuery({
    queryStringKey: 'code',
  });

  const navigate = useNavigate();
  const mutation = useOAuthLogin();

  useEffect(() => {
    if (gitHubAuthCode) {
      mutation.mutate(
        { gitHubAuthCode },
        {
          onSuccess: () => {
            return navigate(`/${ROUTE.reviewLinks}`, { replace: true });
          },
          onError: () => {
            return navigate(ROUTE.home, { replace: true });
          },
        },
      );
    }
  }, []);

  return <h3>로그인 중...</h3>;
};

export default OAuthCallbackPage;
