import { useState } from 'react';

import { EDITOR_ANSWER_CLASS_NAME, HIGHLIGHT_EVENT_NAME, HIGHLIGHT_SPAN_CLASS_NAME } from '@/constants';
import { EditorAnswerMap, EditorLine, HighlightResponseData, ReviewAnswerResponseData } from '@/types';
import {
  getEndLineOffset,
  getStartLineOffset,
  getRemovedHighlightList,
  findSelectionInfo,
  getUpdatedBlockByHighlight,
  removeSelection,
  SelectionInfo,
  trackEventInAmplitude,
} from '@/utils';

import { UseLongPressHighlightPositionReturn } from './useLongPressHighlightPosition';
import useMutateHighlight from './useMutateHighlight';

interface UseHighlightProps extends UseLongPressHighlightPositionReturn {
  questionId: number;
  answerList: ReviewAnswerResponseData[];
  isEditable: boolean;
  handleErrorModal: (isError: boolean) => void;
  handleModalMessage: (message: string) => void;
  resetHighlightMenuPosition: () => void;
}
interface RemovalTarget {
  answerId: number;
  lineIndex: number;
  highlightIndex: number;
}

const HIGHLIGHT_ERROR_MESSAGES = {
  addFailure: '형광펜 추가에 실패했어요. 다시 시도해주세요.',
  deleteFailure: '형광펜 삭제에 실패했어요. 다시 시도해주세요.',
};

const findBlockHighlightListFromAnswer = (answerHighlightList: HighlightResponseData[], lineIndex: number) => {
  return answerHighlightList.find((i) => i.lineIndex === lineIndex)?.ranges || [];
};

const makeBlockListByText = (content: string, answerHighlightList: HighlightResponseData[]): EditorLine[] => {
  return content.split('\n').map((text, index) => ({
    lineIndex: index,
    text,
    highlightList: findBlockHighlightListFromAnswer(answerHighlightList, index),
  }));
};

const makeInitialEditorAnswerMap = (answerList: ReviewAnswerResponseData[]) => {
  const initialEditorAnswerMap: EditorAnswerMap = new Map();

  answerList.forEach((answer, index) => {
    initialEditorAnswerMap.set(answer.id, {
      answerId: answer.id,
      content: answer.content,
      answerIndex: index,
      lineList: makeBlockListByText(answer.content, answer.highlights),
    });
  });

  return initialEditorAnswerMap;
};

const useHighlight = ({
  questionId,
  answerList,
  isEditable,
  updateHighlightMenuPositionByLongPress,
  resetHighlightMenuPosition,
  handleErrorModal,
  handleModalMessage,
}: UseHighlightProps) => {
  const [editorAnswerMap, setEditorAnswerMap] = useState<EditorAnswerMap>(makeInitialEditorAnswerMap(answerList));

  // span 클릭 시, 제공되는 형광펜 삭제 기능 타겟
  const [longPressRemovalTarget, setLongPressRemovalTarget] = useState<RemovalTarget | null>(null);

  const resetLongPressRemovalTarget = () => setLongPressRemovalTarget(null);

  const updateEditorAnswerMap = (newEditorAnswerMap: EditorAnswerMap) => setEditorAnswerMap(newEditorAnswerMap);

  const resetHighlightMenu = () => {
    removeSelection();
    resetHighlightMenuPosition();
    resetLongPressRemovalTarget();
  };

  const { mutate: mutateHighlight } = useMutateHighlight({
    questionId,
    updateEditorAnswerMap,
    resetHighlightMenu,
    handleErrorModal,
  });

  const addHighlightByDrag = () => {
    trackEventInAmplitude(HIGHLIGHT_EVENT_NAME.addHighlightByDrag);

    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;
    const newEditorAnswerMap: EditorAnswerMap | undefined = selectionInfo.isSameAnswer
      ? addSingleAnswerHighlight(selectionInfo)
      : addMultipleAnswerHighlight(selectionInfo);
    if (!newEditorAnswerMap) return;

    mutateHighlight(newEditorAnswerMap, {
      onError: () => {
        handleModalMessage(HIGHLIGHT_ERROR_MESSAGES.addFailure);
      },
    });
  };
  // NOTE :공백으로 이루어진 개행용 문자열의 highlightList는 빈배열로 유지한다

  const addMultipleAnswerHighlight = (selectionInfo: SelectionInfo) => {
    const { startAnswer, endAnswer } = selectionInfo;
    const newEditorAnswerMap = new Map(editorAnswerMap);
    if (!startAnswer || !endAnswer) return;

    [...newEditorAnswerMap.keys()].forEach((answerId, answerIndex) => {
      if (startAnswer.id === answerId) {
        const { lineIndex, offset } = startAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { lineList } = targetAnswer;

        const newLineList: EditorLine[] = lineList.map((line, index) => {
          if (line.text.trim() === '') return line;
          if (index < lineIndex) return line;
          if (index > lineIndex) {
            return {
              ...line,
              highlightList: [{ startIndex: 0, endIndex: line.text.length - 1 }],
            };
          }
          return getUpdatedBlockByHighlight({
            blockTextLength: line.text.length,
            lineIndex: index,
            startIndex: offset,
            endIndex: line.text.length - 1,
            lineList,
          });
        });

        newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
      }

      if (startAnswer.index < answerIndex && endAnswer.index > answerIndex) {
        const targetAnswer = newEditorAnswerMap.get(answerId);
        if (!targetAnswer) return;
        const { lineList } = targetAnswer;

        const newLineList = lineList.map((line) => {
          if (line.text.trim() === '') return line;

          return {
            ...line,
            highlightList: [{ startIndex: 0, endIndex: line.text.length - 1 }],
          };
        });

        newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
      }

      if (endAnswer.id === answerId) {
        const { lineIndex, offset } = endAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { lineList } = targetAnswer;

        const newLineList = lineList.map((line, index) => {
          if (line.text.trim() === '') return line;
          if (index > lineIndex) return line;
          if (index < lineIndex) {
            return {
              ...line,
              highlightList: [{ startIndex: 0, endIndex: line.text.length - 1 }],
            };
          }

          return getUpdatedBlockByHighlight({
            blockTextLength: line.text.length,
            lineIndex: index,
            startIndex: 0,
            endIndex: offset,
            lineList,
          });
        });

        newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
      }
    });

    return newEditorAnswerMap;
  };

  const addSingleAnswerHighlight = (selectionInfo: SelectionInfo) => {
    const { startLineIndex, endLineIndex, startAnswer } = selectionInfo;
    if (!startAnswer) return;

    const newEditorAnswerMap = new Map(editorAnswerMap);
    const answerId = startAnswer.id;
    const targetAnswer = newEditorAnswerMap.get(answerId);

    if (!targetAnswer) return;

    const newLineList: EditorLine[] = targetAnswer.lineList.map((line, index, array) => {
      if (line.text.trim() === '') return line;
      if (index < startLineIndex) return line;
      if (index > endLineIndex) return line;

      if (index === startLineIndex) {
        const { startIndex, endIndex } = getStartLineOffset(selectionInfo, line);

        return getUpdatedBlockByHighlight({
          blockTextLength: line.text.length,
          lineIndex: index,
          startIndex,
          endIndex,
          lineList: array,
        });
      }

      if (index === endLineIndex) {
        const endIndex = getEndLineOffset(selectionInfo);

        return getUpdatedBlockByHighlight({
          blockTextLength: line.text.length,
          lineIndex: index,
          startIndex: 0,
          endIndex,
          lineList: array,
        });
      }

      return {
        ...line,
        highlightList: [{ startIndex: 0, endIndex: line.text.length - 1 }],
      };
    });

    newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });

    return newEditorAnswerMap;
  };

  const removeHighlightByDrag = () => {
    trackEventInAmplitude(HIGHLIGHT_EVENT_NAME.removeHighlightByDrag);

    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    const newEditorAnswerMap: EditorAnswerMap | undefined = selectionInfo.isSameAnswer
      ? removeSingleAnswerHighlight(selectionInfo)
      : removeMultipleAnswerHighlight(selectionInfo);

    if (!newEditorAnswerMap) return;

    mutateHighlight(newEditorAnswerMap);
  };

  const removeSingleAnswerHighlight = (selectionInfo: SelectionInfo) => {
    const { startLineIndex, endLineIndex, startAnswer } = selectionInfo;
    if (!startAnswer) return;

    const newEditorAnswerMap = new Map(editorAnswerMap);
    const answerId = startAnswer.id;
    const targetAnswer = newEditorAnswerMap.get(answerId);

    if (!targetAnswer) return;

    const newLineList = targetAnswer.lineList.map((line, index) => {
      if (line.text.trim() === '') return line;
      if (index < startLineIndex) return line;
      if (index > endLineIndex) return line;

      if (index === startLineIndex) {
        const { startIndex, endIndex } = getStartLineOffset(selectionInfo, line);

        return {
          ...line,
          highlightList: getRemovedHighlightList({
            blockTextLength: line.text.length,
            highlightList: line.highlightList,
            startIndex,
            endIndex,
          }),
        };
      }

      if (index === endLineIndex) {
        const endIndex = getEndLineOffset(selectionInfo);
        return {
          ...line,
          highlightList: getRemovedHighlightList({
            blockTextLength: line.text.length,
            highlightList: line.highlightList,
            startIndex: 0,
            endIndex,
          }),
        };
      }
      return {
        ...line,
        highlightList: [],
      };
    });

    newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });

    return newEditorAnswerMap;
  };
  const removeMultipleAnswerHighlight = (selectionInfo: SelectionInfo) => {
    const { startAnswer, endAnswer } = selectionInfo;
    const newEditorAnswerMap = new Map(editorAnswerMap);
    if (!startAnswer || !endAnswer) return;

    [...newEditorAnswerMap.keys()].forEach((answerId, answerIndex) => {
      if (answerId === startAnswer.id) {
        const { lineIndex, offset } = startAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { lineList } = targetAnswer;

        const newLineList = lineList.map((line, index) => {
          if (line.text.trim() === '') return line;
          if (index < lineIndex) return line;

          if (index > lineIndex) {
            return {
              ...line,
              highlightList: [],
            };
          }

          return {
            ...line,
            highlightList: getRemovedHighlightList({
              blockTextLength: line.text.length,
              highlightList: line.highlightList,
              startIndex: offset,
              endIndex: line.text.length - 1,
            }),
          };
        });

        newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
      }

      if (answerId === endAnswer.id) {
        const { lineIndex, offset } = endAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { lineList } = targetAnswer;

        const newLineList = lineList.map((line, index) => {
          if (line.text.trim() === '') return line;
          if (index > lineIndex) return line;

          if (index < lineIndex) {
            return {
              ...line,
              highlightList: [],
            };
          }

          return {
            ...line,
            highlightList: getRemovedHighlightList({
              blockTextLength: line.text.length,
              highlightList: line.highlightList,
              startIndex: 0,
              endIndex: offset,
            }),
          };
        });

        newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
      }

      if (answerIndex > startAnswer.index && answerIndex < endAnswer.index) {
        const targetAnswer = newEditorAnswerMap.get(answerId);
        if (!targetAnswer) return;

        const newLineList: EditorLine[] = targetAnswer.lineList.map((line) => ({
          ...line,
          highlightList: [],
        }));
        newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
      }
    });

    return newEditorAnswerMap;
  };

  const isSingleCharacterSelected = () => {
    const selection = document.getSelection();

    if (selection) {
      const { anchorNode, anchorOffset, focusNode, focusOffset } = selection;
      const isSameSelectedNode = anchorNode === focusNode && Math.abs(anchorOffset - focusOffset) === 1;

      return isSameSelectedNode;
    }
    return false;
  };

  const handleLongPressLine = (event: React.MouseEvent | React.TouchEvent) => {
    if (!isEditable) return;
    if (isSingleCharacterSelected()) return;

    const target = event.target as HTMLElement;
    if (!target.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME)) return;
    const answerElement = target.closest(`.${EDITOR_ANSWER_CLASS_NAME}`);
    if (!answerElement) return;
    const id = answerElement.getAttribute('data-answer')?.split('-')[0];
    if (!id) return;
    const targetAnswer = editorAnswerMap.get(Number(id));
    if (!targetAnswer) return;

    const rect = target.getClientRects()[0];
    if (!target.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME)) return;
    const lineIndex = target.parentElement?.getAttribute('data-index');
    const start = target.getAttribute('data-highlight-start');
    const end = target.getAttribute('data-highlight-end');
    if (!lineIndex || !start || !end) return;
    const { highlightList } = targetAnswer.lineList[Number(lineIndex)];
    const highlightIndex = highlightList.findIndex((i) => i.startIndex === Number(start) && i.endIndex === Number(end));

    setLongPressRemovalTarget({
      answerId: targetAnswer.answerId,
      lineIndex: Number(lineIndex),
      highlightIndex: Number(highlightIndex),
    });

    updateHighlightMenuPositionByLongPress(rect);
  };

  const removeHighlightByLongPress = async () => {
    trackEventInAmplitude(HIGHLIGHT_EVENT_NAME.removeHighlightByLongPress);

    if (!longPressRemovalTarget) return;

    const { answerId, lineIndex, highlightIndex } = longPressRemovalTarget;

    const newEditorAnswerMap: EditorAnswerMap = new Map(editorAnswerMap);
    const targetAnswer = newEditorAnswerMap.get(answerId);
    if (!targetAnswer) return;

    const newLineList = [...targetAnswer.lineList];
    const targetBlock = newLineList[lineIndex];
    const newHighlightList = [...targetBlock.highlightList];

    newHighlightList.splice(highlightIndex, 1);
    const newTargetBlock: EditorLine = { ...targetBlock, highlightList: newHighlightList };

    newLineList.splice(lineIndex, 1, newTargetBlock);
    newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });

    mutateHighlight(newEditorAnswerMap, {
      onError: () => {
        handleModalMessage(HIGHLIGHT_ERROR_MESSAGES.deleteFailure);
      },
    });
  };

  return {
    editorAnswerMap,
    addHighlightByDrag,
    removeHighlightByDrag,
    handleLongPressLine,
    removeHighlightByLongPress,
    longPressRemovalTarget,
    resetLongPressRemovalTarget,
  };
};

export default useHighlight;
