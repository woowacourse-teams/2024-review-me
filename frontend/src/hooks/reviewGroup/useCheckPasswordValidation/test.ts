import { renderHook, waitFor } from '@testing-library/react';

import { VALIDATED_PASSWORD } from '@/mocks/mockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';

import useCheckPasswordValidation from '.';

describe('비밀번호 조회 테스트', () => {
  it('비밀번호가 유효하면 isValidAccess가 true이다 ', async () => {
    const REVIEW_REQUEST_CODE = 'ABCD1234';
    const INVALIDATED_PASSWORD = '1111';

    const { result } = renderHook(
      () =>
        useCheckPasswordValidation({
          reviewRequestCode: REVIEW_REQUEST_CODE,
          groupAccessCode: INVALIDATED_PASSWORD,
          onSuccess: () => {},
          onError: () => {},
        }),
      {
        wrapper: QueryClientWrapper,
      },
    );

    await waitFor(() => {
      expect(result.current.status).toBe('success');
    });

    expect(result.current.data).toBeDefined();
    expect(result.current.data instanceof Error).toBeFalsy();

    if (!(result.current.data instanceof Error)) {
      expect(result.current.data?.isValidAccess).toBeFalsy();
    }
  });

  it('비밀번호가 유효하지 않으면 isValidAccess가 false이다 ', async () => {
    const REVIEW_REQUEST_CODE = 'ABCD1234';
    const { result } = renderHook(
      () =>
        useCheckPasswordValidation({
          reviewRequestCode: REVIEW_REQUEST_CODE,
          groupAccessCode: VALIDATED_PASSWORD,
          onSuccess: () => {},
          onError: () => {},
        }),
      {
        wrapper: QueryClientWrapper,
      },
    );

    await waitFor(() => {
      expect(result.current.status).toBe('success');
    });

    expect(result.current.data).toBeDefined();
    expect(result.current.data instanceof Error).toBeFalsy();

    if (!(result.current.data instanceof Error)) {
      expect(result.current.data?.isValidAccess).toBeFalsy();
    }
  });
});
