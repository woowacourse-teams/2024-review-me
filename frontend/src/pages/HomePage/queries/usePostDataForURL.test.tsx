import { renderHook, act, waitFor } from '@testing-library/react';

import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';

import { CREATED_GROUP_DATA } from '../../../mocks/mockData/group';
import usePostDataForURL from './usePostDataForURL';

describe('usePostDataForURL', () => {
  it('URL 및 확인 코드를 발급받을 수 있다.', async () => {
    // given
    const dataForURL = {
      revieweeName: 'ollie',
      projectName: 'review-me',
    };

    const { result } = renderHook(() => usePostDataForURL(), { wrapper: QueryClientWrapper });

    // when
    act(() => {
      result.current.mutate(dataForURL);
    });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));

    // then
    expect(result.current.data).toEqual(CREATED_GROUP_DATA);
  });
});
