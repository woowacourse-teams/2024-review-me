import { renderHook, act, waitFor } from '@testing-library/react';

import { VALID_REVIEW_REQUEST_CODE } from '@/mocks/mockData/group';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';
import { DataForReviewRequestCode } from '@/types';
import { testWithAuthCookie } from '@/utils';

import usePostDataForReviewRequestCode from '.';

describe('usePostDataForReviewRequestCode', () => {
  // 공통 로직: renderHook 호출 및 초기화
  const setupHook = () => {
    const { result } = renderHook(
      () =>
        usePostDataForReviewRequestCode({
          handleAPIError: (error: Error) => {
            console.error(error);
          },
          handleAPISuccess: (data: any) => {},
        }),
      { wrapper: QueryClientWrapper },
    );
    return result;
  };

  // 공통 테스트 로직
  const testReviewRequestCode = async (dataForReviewRequestCode: DataForReviewRequestCode, expectedCode: string) => {
    const result = setupHook();

    act(() => {
      result.current.mutate(dataForReviewRequestCode);
    });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));

    expect(result.current.data.reviewRequestCode).toEqual(expectedCode);
  };

  it('비회원 - ReviewRequestCode를 발급받을 수 있다.', async () => {
    const dataForReviewRequestCode = {
      revieweeName: 'ollie',
      projectName: 'review-me',
      groupAccessCode: '1234',
    };

    await testReviewRequestCode(dataForReviewRequestCode, VALID_REVIEW_REQUEST_CODE.nonMember);
  });

  it('회원용 - ReviewRequestCode를 발급받을 수 있다.', async () => {
    const dataForReviewRequestCode = {
      revieweeName: 'ollie',
      projectName: 'review-me',
    };

    await testWithAuthCookie({
      authState: 'both',
      callback: async () => await testReviewRequestCode(dataForReviewRequestCode, VALID_REVIEW_REQUEST_CODE.member),
    });
  });
});
