import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { renderHook, waitFor } from '@testing-library/react';
import React from 'react';

import useGetReviewList from './index';

const queryClient = new QueryClient();

const wrapper = ({ children }: { children: React.ReactNode }) => (
  <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

describe('리뷰 목록 페이지 API 연동 테스트', () => {
  test('성공적으로 데이터를 가져온다', async () => {
    const GROUP_ACCESS_CODE = '2024';
    const REVIEW_REQUEST_CODE = 'ABCD1234';
    const { result } = renderHook(() => useGetReviewList(GROUP_ACCESS_CODE, REVIEW_REQUEST_CODE), { wrapper });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });
  });
});
