import { renderHook, waitFor } from '@testing-library/react';
import { act } from 'react';

import { MOCK_AUTH_TOKEN_NAME } from '@/mocks/mockData';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';
import { EditorAnswer, EditorAnswerMap } from '@/types';

import useMutateHighlight, { UseMutateHighlightProps } from '.';

describe('하이라이트 요청 테스트', () => {
  test('하이라이트 오쳥을 성공한다.', async () => {
    const ANSWER: EditorAnswer = {
      content: '테스',
      answerId: 123,
      answerIndex: 0,
      lineList: [{ lineIndex: 0, text: '테', highlightList: [{ startIndex: 0, endIndex: 0 }] }],
    };

    const EDITOR_ANSWER_MAP: EditorAnswerMap = new Map([[1, ANSWER]]);

    const props: UseMutateHighlightProps = {
      questionId: 1,
      updateEditorAnswerMap: () => {},
      resetHighlightButton: () => {},
      handleErrorModal: () => {},
    };

    //쿠키 생성
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=2024-review-me`;

    const { result } = renderHook(() => useMutateHighlight(props), {
      wrapper: QueryClientWrapper,
    });

    await act(async () => {
      await result.current.mutateAsync(EDITOR_ANSWER_MAP);
      waitFor(() => expect(result.current.status).toBe('success'));
    });

    // 쿠키 삭제
    document.cookie = `${MOCK_AUTH_TOKEN_NAME}=; max-age=-1`;
  });
});
