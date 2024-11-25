import { ERROR_BOUNDARY_IGNORE_ERROR } from '@/constants';
import { EditorAnswerMap, HighlightPostPayload } from '@/types';

import createApiErrorMessage from './apiErrorMessageCreator';
import endPoint from './endpoints';

export const transformHighlightData = (editorAnswerMap: EditorAnswerMap, questionId: number): HighlightPostPayload => {
  // NOTE: 하이라이트가 있는 답변만 서버에 보내줌 (줄에 하이라이트가 없으면 빈배열)
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

export const isValidPayload = (payload: HighlightPostPayload) => {
  return payload.highlights.every((highlight) => highlight.lines.every((line) => line.ranges.length > 0));
};

export const postHighlight = async (editorAnswerMap: EditorAnswerMap, questionId: number) => {
  const postingData = transformHighlightData(editorAnswerMap, questionId);

  if (!isValidPayload(postingData)) return console.error('유효하지 않은 형광펜 데이터입니다');

  try {
    const response = await fetch(endPoint.postingHighlight, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify(postingData),
    });

    if (!response.ok) {
      throw new Error(ERROR_BOUNDARY_IGNORE_ERROR + createApiErrorMessage(response.status));
    }
  } catch (error) {
    throw new Error(`${ERROR_BOUNDARY_IGNORE_ERROR}-형광펜 API 요청 실패`);
  }
};
