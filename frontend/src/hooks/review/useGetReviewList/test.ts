import { renderHook, waitFor } from '@testing-library/react';

import { MOCK_AUTH_TOKEN_NAME } from '@/mocks/mockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';

import useGetReviewList from './index';

describe('리뷰 목록 페이지 API 연동 테스트', () => {
  test('성공적으로 데이터를 가져온다', async () => {
    //쿠키 생성
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=2024-review-me`;

    const { result } = renderHook(() => useGetReviewList(), {
      wrapper: QueryClientWrapper,
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });
  });
  // 쿠키 삭제
  document.cookie = `${MOCK_AUTH_TOKEN_NAME}=; max-age=-1`;
});
