import { http, HttpResponse } from 'msw';

import endPoint, { OAUTH_API_URL, OAUTH_LOGIN_API_PARAMS } from '@/apis/endpoints';

import { memberOnlyCookie, MOCK_LOGIN_TOKEN_NAME, MOCK_USER_PROFILE } from '../mockData';

import { authorizeWithCookie } from './cookies';

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

const getUserProfile = () =>
  http.get(endPoint.gettingUserProfile, ({ cookies }) => {
    const handleAPI = () => {
      return HttpResponse.json(MOCK_USER_PROFILE, { status: 200 });
    };

    return authorizeWithCookie({
      cookies,
      validateCookieNames: memberOnlyCookie,
      callback: handleAPI,
    });
  });

const postOAuthLogout = () =>
  http.post(endPoint.postingOAuthLogout, ({ cookies }) => {
    const handleAPI = () => {
      // 로그아웃 성공 시 쿠키 삭제
      return new HttpResponse(null, {
        status: 204,
        headers: {
          'Set-Cookie': `${MOCK_LOGIN_TOKEN_NAME}=; Max-Age=0; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT;`,
        },
      });
    };

    return authorizeWithCookie<HttpResponse>({
      cookies,
      validateCookieNames: memberOnlyCookie,
      callback: handleAPI,
    });
  });

const oAuthHandler = [postOAuthLogin(), getUserProfile(), postOAuthLogout()];

export default oAuthHandler;
