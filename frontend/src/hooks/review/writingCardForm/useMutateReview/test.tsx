import { renderHook, waitFor } from '@testing-library/react';
import { act } from 'react';

import { REVIEW_FORM_RESULT_DATA } from '@/mocks/mockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';

import useMutateReview from '.';

describe('리뷰 생성 요청 테스트', () => {
  test('성공적으로 리뷰를 생성한다.', async () => {
    const { result } = renderHook(() => useMutateReview({ openErrorModal: () => {} }), { wrapper: QueryClientWrapper });

    act(() => {
      result.current.postReview(REVIEW_FORM_RESULT_DATA);
    });

    await waitFor(() => result.current.reviewMutation.isSuccess);

    expect(result.current.reviewMutation.isSuccess).toBe(true);
  });
});
