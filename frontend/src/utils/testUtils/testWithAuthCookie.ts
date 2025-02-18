import { MOCK_AUTH_TOKEN_NAME, MOCK_LOGIN_TOKEN_NAME } from '@/mocks/mockData';

type AuthCookieState = 'member' | 'nonMember' | 'both';

interface TestWithAuthCookieParams {
  authState: AuthCookieState;
  callback: () => Promise<void> | void;
}

const testWithAuthCookie = async ({ authState, callback }: TestWithAuthCookieParams) => {
  // 쿠키 추가
  if (authState === 'nonMember') {
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=2024-review-me`;
  }
  if (authState === 'member') {
    document.cookie = `${MOCK_LOGIN_TOKEN_NAME}=2024-review-me`;
  }
  if (authState === 'both') {
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=2024-review-me`;
    document.cookie = `${MOCK_LOGIN_TOKEN_NAME}=2024-review-me`;
  }

  try {
    await callback();
  } finally {
    // 쿠키 삭제
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=; max-age=-1`;
    document.cookie = `${MOCK_LOGIN_TOKEN_NAME}=; max-age=-1`;
  }
};

export default testWithAuthCookie;
