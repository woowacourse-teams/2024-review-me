import { EditorAnswerMap, HighlightPostPayload } from '@/types';

import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

const transformHighlightData = (editorAnswerMap: EditorAnswerMap, questionId: number): HighlightPostPayload => {
  // NOTE: 하이라이트가 있는 답변만 서버에 보내줌
  return {
    questionId,
    highlights: [...editorAnswerMap.values()]
      .filter((answer) => answer.lineList.some((line) => line.highlightList.length > 0))
      .map((answer) => ({
        answerId: answer.answerId,
        lines: answer.lineList
          .filter((line) => line.highlightList.length > 0)
          .map((line) => ({
            index: line.lineIndex,
            ranges: line.highlightList,
          })),
      })),
  };
};

export const postHighlight = async (editorAnswerMap: EditorAnswerMap, questionId: number) => {
  const postingData = transformHighlightData(editorAnswerMap, questionId);
  const response = await fetch(endPoint.postingHighlight, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
    body: JSON.stringify(postingData),
  });

  if (!response.ok) {
    throw new Error(createApiErrorMessage(response.status));
  }
};
