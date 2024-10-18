import { renderHook, waitFor } from '@testing-library/react';

import { DETAILED_PAGE_MOCK_API_SETTING_VALUES } from '@/mocks/mockData/detailedReviewMockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';
import { testWithAuthCookie } from '@/utils';

import useGetDetailedReview from '.';

describe('리뷰 상세페이지 데이터 요청 테스트', () => {
  it('유효힌 id,memberId 사용해야 라뷰 상세 페이지 데이터를 불러온다.', async () => {
    const testReviewDetailAPI = async () => {
      const { reviewId } = DETAILED_PAGE_MOCK_API_SETTING_VALUES;
      const { result } = renderHook(() => useGetDetailedReview({ reviewId }), {
        wrapper: QueryClientWrapper,
      });

      await waitFor(() => {
        expect(result.current.status).toBe('success');
      });

      expect(result.current.data).toBeDefined();
    };

    await testWithAuthCookie(testReviewDetailAPI);
  });
});
