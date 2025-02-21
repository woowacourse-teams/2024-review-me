import { HttpResponse } from 'msw';

/**
 * 쿠키 인증이 필요한 api 요청 시, 쿠키 인증 확인 후 콜백으로 받은 api 목핸들러 작업을 할 수 있게 진행
 * @param callback : 쿠키 인증 확인 후 진행할 api 목핸들러 작업
 */

interface AuthorizeWithCookieParams {
  cookies: Record<string, string>;
  validateCookieNames: string[];
  callback: () => HttpResponse;
}

export const authorizeWithCookie = <T extends HttpResponse>({
  cookies,
  validateCookieNames,
  callback,
}: AuthorizeWithCookieParams): T | HttpResponse => {
  const hasValidCookie = validateCookieNames.some((cookieName) => cookies[cookieName]);

  if (!hasValidCookie) {
    return HttpResponse.json({ error: '인증 관련 쿠키 없음' }, { status: 401 });
  }

  // 인증 성공 시 콜백 실행
  return callback();
};
