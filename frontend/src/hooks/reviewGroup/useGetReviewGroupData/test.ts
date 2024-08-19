import { renderHook, waitFor } from '@testing-library/react';

import { VALID_REVIEW_GROUP_REVIEW_REQUEST_CODE } from '@/mocks/mockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';

import useGetReviewGroupData from '.';

describe('리뷰 연결 페이지 리뷰 그룹 데이터 요청 테스트', () => {
  it('유효한 reviewRequestCode여야 라뷰 상세 페이지 데이터를 불러온다.', async () => {
    const { result } = renderHook(
      () => useGetReviewGroupData({ reviewRequestCode: VALID_REVIEW_GROUP_REVIEW_REQUEST_CODE }),
      {
        wrapper: QueryClientWrapper,
      },
    );

    await waitFor(() => {
      expect(result.current.status).toBe('success');
    });

    expect(result.current.data).toBeDefined();
  });
});
