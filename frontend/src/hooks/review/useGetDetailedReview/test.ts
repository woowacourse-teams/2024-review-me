import { renderHook, waitFor } from '@testing-library/react';

import { MOCK_AUTH_TOKEN_NAME } from '@/mocks/mockData';
import { DETAILED_PAGE_MOCK_API_SETTING_VALUES } from '@/mocks/mockData/detailedReviewMockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';

import useGetDetailedReview from '.';
// 아래의 테스트는 로그인이 유효하다는 가정하에서 진행

describe('리뷰 상세페이지 데이터 요청 테스트', () => {
  it('유효힌 id,memberId 사용해야 라뷰 상세 페이지 데이터를 불러온다.', async () => {
    // 쿠키 생성
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=2024-review-me`;

    const { reviewId } = DETAILED_PAGE_MOCK_API_SETTING_VALUES;
    const { result } = renderHook(() => useGetDetailedReview({ reviewId }), {
      wrapper: QueryClientWrapper,
    });

    await waitFor(() => {
      expect(document.cookie).toEqual(`${MOCK_AUTH_TOKEN_NAME}=2024-review-me`);
      expect(result.current.status).toBe('success');
    });

    expect(result.current.data).toBeDefined();

    // 쿠키 삭제
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=; max-age=-1`;
  });
});
