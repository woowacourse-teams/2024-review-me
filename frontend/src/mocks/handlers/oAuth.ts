import { http, HttpResponse } from 'msw';

import { OAUTH_API_URL, OAUTH_LOGIN_API_PARAMS } from '@/apis/endpoints';

import { MOCK_LOGIN_TOKEN_NAME } from '../mockData';

const postOAuthLogin = () =>
  http.post(new RegExp(`^${OAUTH_API_URL}`), async ({ request }) => {
    const url = new URL(request.url);
    const gitHubAuthCode = url.searchParams.get(OAUTH_LOGIN_API_PARAMS.queryString.code);
    // 로그인 성공 시 세션 쿠키 생성
    if (gitHubAuthCode) {
      return new HttpResponse(null, {
        status: 204,
        headers: { 'Set-cookie': `${MOCK_LOGIN_TOKEN_NAME}=2024-review-me` },
      });
    }

    return HttpResponse.json({ error: '깃허브 인증에 실패했어요' }, { status: 401 });
  });

const oAuthHandler = [postOAuthLogin()];

export default oAuthHandler;
