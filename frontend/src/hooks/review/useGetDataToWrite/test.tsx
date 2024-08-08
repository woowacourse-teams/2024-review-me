import { renderHook, waitFor } from '@testing-library/react';

import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';

import useGetDataToWrite from '.';

describe('리뷰 작성을 위한 데이터 요청 테스트', () => {
  test('성공적으로 데이터를 가져온다.', async () => {
    const REVIEW_REQUEST_CODE = 'ABCD1234';

    const { result } = renderHook(() => useGetDataToWrite({ reviewRequestCode: REVIEW_REQUEST_CODE }), {
      wrapper: QueryClientWrapper,
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });
  });
});
