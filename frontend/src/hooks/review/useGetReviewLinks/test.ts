import { renderHook, waitFor } from '@testing-library/react';

import { MOCK_USER_PROFILE } from '@/mocks/mockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';
import { testWithAuthCookie } from '@/utils';

import useGetReviewLinks from '.';

describe('회원이 생성한 리뷰 그룹 목록 조회 테스트', () => {
  it('회원이 생성한 리뷰 그룹 목록을 성공적으로 불러온다.', async () => {
    const testReviewLinksAPI = async () => {
      const { result } = renderHook(() => useGetReviewLinks({ memberId: MOCK_USER_PROFILE.memberId }), {
        wrapper: QueryClientWrapper,
      });

      await waitFor(() => {
        expect(result.current.isSuccess).toBe(true);
      });

      expect(result.current.data).toBeDefined();
    };

    await testWithAuthCookie({ authState: 'member', callback: testReviewLinksAPI });
  });
});
