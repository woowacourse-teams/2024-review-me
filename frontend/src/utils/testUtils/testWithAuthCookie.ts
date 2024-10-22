import { MOCK_AUTH_TOKEN_NAME } from '@/mocks/mockData';

const testWithAuthCookie = async (callback: () => Promise<void> | void) => {
  // 쿠키 추가
  document.cookie = `${MOCK_AUTH_TOKEN_NAME}=2024-review-me`;

  try {
    await callback();
  } finally {
    // 쿠키 삭제
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=; max-age=-1`;
  }
};

export default testWithAuthCookie;
