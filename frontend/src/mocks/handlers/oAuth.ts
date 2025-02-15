import { http, HttpResponse } from 'msw';

import { OAUTH_API_URL, OAUTH_LOGIN_API_PARAMS } from '@/apis/endpoints';

import { MOCK_LOGIN_TOKEN_NAME } from '../mockData';

const postOAuthLogin = () => {
  // TODO: 아직 api 구현이 완료되지 않은 관계로 일단 get으로 하고, 추후 post로 변경
  return http.get(new RegExp(`^${OAUTH_API_URL}`), async ({ request }) => {
    const url = new URL(request.url);
    const gitHubAuthCode = url.searchParams.get(OAUTH_LOGIN_API_PARAMS.queryString.code);

    if (gitHubAuthCode) {
      return new HttpResponse(null, {
        status: 204,
        headers: { 'Set-cookie': `${MOCK_LOGIN_TOKEN_NAME}=2024-review-me` },
      });
    }

    return HttpResponse.json({ error: '깃허브 인증에 실패했어요' }, { status: 401 });
  });
};

const oAuthHandler = [postOAuthLogin()];

export default oAuthHandler;
