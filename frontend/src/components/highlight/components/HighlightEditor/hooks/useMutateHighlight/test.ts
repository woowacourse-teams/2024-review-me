import { renderHook, waitFor } from '@testing-library/react';
import { act } from 'react';

import { isValidPayload, transformHighlightData } from '@/apis/highlight';
import QueryClientWrapper from '@/queryTestSetup/QueryClientWrapper';
import { EditorAnswer, EditorAnswerMap } from '@/types';
import { testWithAuthCookie } from '@/utils';

import useMutateHighlight, { UseMutateHighlightProps } from '.';

describe('하이라이트 요청 테스트', () => {
  test('API 요청 보내는 데이터가 유효하면(= 하이라이트가 적용된 답변만 보낸다), 하이라이트 요청을 성공한다.', async () => {
    const ANSWER: EditorAnswer = {
      content: '테스',
      answerId: 123,
      answerIndex: 0,
      lineList: [{ lineIndex: 0, text: '테', highlightList: [{ startIndex: 0, endIndex: 0 }] }],
    };

    const EDITOR_ANSWER_MAP: EditorAnswerMap = new Map([[1, ANSWER]]);
    const QUESTION_ID = 1;

    const props: UseMutateHighlightProps = {
      questionId: QUESTION_ID,
      updateEditorAnswerMap: () => {},
      resetHighlightButton: () => {},
      handleErrorModal: () => {},
    };

    const testHighlightAPI = async () => {
      const data = transformHighlightData(EDITOR_ANSWER_MAP, QUESTION_ID);
      expect(isValidPayload(data)).toBeTruthy();

      const { result } = renderHook(() => useMutateHighlight(props), {
        wrapper: QueryClientWrapper,
      });

      await act(async () => {
        await result.current.mutateAsync(EDITOR_ANSWER_MAP);
        waitFor(() => expect(result.current.isSuccess).toBeTruthy());
      });
    };

    await testWithAuthCookie(testHighlightAPI);
  });
});
