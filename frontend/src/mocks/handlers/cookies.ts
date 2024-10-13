import { HttpResponse } from 'msw';

import { MOCK_AUTH_TOKEN_NAME } from '../mockData';

/**
 * 쿠키 인증이 필요한 api 요청 시, 쿠키 인증 확인 후 콜백으로 받은 api 목핸들러 작업을 할 수 있게 진행
 * @param callback : 쿠키 인증 확인 후 진행할 api 목핸들러 작업
 */
export const withAuthCookie = <T>(cookies: Record<string, string>, callback: () => T) => {
  if (!cookies[MOCK_AUTH_TOKEN_NAME]) {
    return HttpResponse.json({ error: '인증 관련 쿠키 없음' }, { status: 401 });
  }

  // 인증 성공 시 콜백 실행
  return callback();
};
