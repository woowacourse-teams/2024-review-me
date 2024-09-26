import { renderHook, waitFor } from '@testing-library/react';

import { VALIDATED_PASSWORD } from '@/mocks/mockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';

import useCheckPasswordValidation from '.';

describe('비밀번호 조회 테스트', () => {
  it('비밀번호가 유효하면 status가 valid이다 ', async () => {
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
    expect(result.current.data?.status).toBe('valid');
  });

  it('비밀번호가 유효하지 않으면 status가 invalid이다 ', async () => {
    const REVIEW_REQUEST_CODE = 'ABCD1234';
    const INVALID_PASSWORD = 'wrongPassword';

    const { result } = renderHook(
      () =>
        useCheckPasswordValidation({
          reviewRequestCode: REVIEW_REQUEST_CODE,
          groupAccessCode: INVALID_PASSWORD,
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
    expect(result.current.data?.status).toBe('invalid');
  });
});
