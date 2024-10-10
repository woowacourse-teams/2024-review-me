import { useState } from 'react';

import { postHighlight } from '@/apis/collection';
import { EDITOR_ANSWER_CLASS_NAME, HIGHLIGHT_SPAN_CLASS_NAME } from '@/constants';
import { EditorAnswerMap, EditorLine, Highlight, ReviewAnswerResponseData } from '@/types';
import {
  getEndBlockOffset,
  getStartBlockOffset,
  getRemovedHighlightList,
  findSelectionInfo,
  getUpdatedBlockByHighlight,
  removeSelection,
  EditorSelectionInfo,
} from '@/utils';

interface UseHighlightProps {
  questionId: number;
  answerList: ReviewAnswerResponseData[];
  isEditAble: boolean;
  hideHighlightToggleButton: () => void;
  updateRemoverPosition: (rect: DOMRect) => void;
  hideRemover: () => void;
}

interface RemovalTarget {
  answerId: number;
  lineIndex: number;
  highlightIndex: number;
}

const findBlockHighlightListFromAnswer = (answerHighlightList: Highlight[], lineIndex: number) => {
  return answerHighlightList.find((i) => i.lineIndex === lineIndex)?.rangeList || [];
};
const makeBlockListByText = (content: string, answerHighlightList: Highlight[]): EditorLine[] => {
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
  isEditAble,
  hideHighlightToggleButton,
  updateRemoverPosition,
  hideRemover,
}: UseHighlightProps) => {
  const [editorAnswerMap, setEditorAnswerMap] = useState<EditorAnswerMap>(makeInitialEditorAnswerMap(answerList));
  // span 클릭 시, 제공되는 형광펜 삭제 기능 타겟
  const [removalTarget, setRemovalTarget] = useState<RemovalTarget | null>(null);

  /**
   * 선택사항, 토글 버튼 지우기
   */
  const resetSelectionAndButton = () => {
    removeSelection();
    hideHighlightToggleButton();
  };

  const addHighlight = async () => {
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;
    const newEditorAnswerMap = selectionInfo.isSameAnswer
      ? addSingleAnswerHighlight(selectionInfo)
      : addMultipleAnswerHighlight(selectionInfo);
    if (!newEditorAnswerMap) return;
    // TODO: 데이터 요청 후, 성공 시 업데이트 하기
    setEditorAnswerMap(newAnswerMap);

    resetSelectionAndButton();
  };

  const addMultipleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
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

        const newLineList = lineList.map((block) => ({
          ...block,
          highlightList: [{ startIndex: 0, endIndex: block.text.length - 1 }],
        }));

        newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
      }

      if (endAnswer.id === answerId) {
        const { lineIndex, offset } = endAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { lineList } = targetAnswer;

        const newLineList = lineList.map((block, index) => {
          if (index > lineIndex) return block;
          if (index < lineIndex) {
            return {
              ...block,
              highlightList: [{ startIndex: 0, endIndex: block.text.length - 1 }],
            };
          }

          return getUpdatedBlockByHighlight({
            blockTextLength: block.text.length,
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

  const addSingleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
    const { startBlockIndex, endBlockIndex, startAnswer } = selectionInfo;
    if (!startAnswer) return;

    const newEditorAnswerMap = new Map(editorAnswerMap);
    const answerId = startAnswer.id;
    const targetAnswer = newEditorAnswerMap.get(answerId);

    if (!targetAnswer) return;

    const newLineList: EditorLine[] = targetAnswer.lineList.map((block, index, array) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const { startIndex, endIndex } = getStartBlockOffset(selectionInfo, block);

        return getUpdatedBlockByHighlight({
          blockTextLength: block.text.length,
          lineIndex: index,
          startIndex,
          endIndex,
          lineList: array,
        });
      }

      if (index === endBlockIndex) {
        const endIndex = getEndBlockOffset(selectionInfo);

        return getUpdatedBlockByHighlight({
          blockTextLength: block.text.length,
          lineIndex: index,
          startIndex: 0,
          endIndex,
          lineList: array,
        });
      }
      return {
        ...block,
        highlightList: [{ startIndex: 0, endIndex: block.text.length }],
      };
    });

    newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
    return newEditorAnswerMap;
  };

  const removeHighlightByDrag = async () => {
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    const newEditorAnswerMap = selectionInfo.isSameAnswer
      ? removeSingleAnswerHighlight(selectionInfo)
      : removeMultipleAnswerHighlight(selectionInfo);

    if (!newEditorAnswerMap) return;

    // TODO: 서버에 API 요청 보내고 성공 한 후 상태 업데이트
    setEditorAnswerMap(newEditorAnswerMap);

    // 선택사항, 토글 버튼 지우기
    resetSelectionAndButton();
  };

  const removeSingleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
    const { startBlockIndex, endBlockIndex, startAnswer } = selectionInfo;
    if (!startAnswer) return;

    const newEditorAnswerMap = new Map(editorAnswerMap);
    const answerId = startAnswer.id;
    const targetAnswer = newEditorAnswerMap.get(answerId);

    if (!targetAnswer) return;

    const newLineList = targetAnswer.lineList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const { startIndex, endIndex } = getStartBlockOffset(selectionInfo, block);

        return {
          ...block,
          highlightList: getRemovedHighlightList({
            blockTextLength: block.text.length,
            highlightList: block.highlightList,
            startIndex,
            endIndex,
          }),
        };
      }
      if (index === endBlockIndex) {
        const endIndex = getEndBlockOffset(selectionInfo);
        return {
          ...block,
          highlightList: getRemovedHighlightList({
            blockTextLength: block.text.length,
            highlightList: block.highlightList,
            startIndex: 0,
            endIndex,
          }),
        };
      }
      return {
        ...block,
        highlightList: [],
      };
    });

    newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });
    return newEditorAnswerMap;
  };

  const removeMultipleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
    const { startAnswer, endAnswer } = selectionInfo;
    const newEditorAnswerMap = new Map(editorAnswerMap);
    if (!startAnswer || !endAnswer) return;

    [...newEditorAnswerMap.keys()].forEach((answerId, answerIndex) => {
      if (answerId === startAnswer.id) {
        const { lineIndex, offset } = startAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { lineList } = targetAnswer;

        const newLineList = lineList.map((block, index) => {
          if (index < lineIndex) return block;

          if (index > lineIndex) {
            return {
              ...block,
              highlightList: [],
            };
          }
          return {
            ...block,
            highlightList: getRemovedHighlightList({
              blockTextLength: block.text.length,
              highlightList: block.highlightList,
              startIndex: offset,
              endIndex: block.text.length - 1,
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

        const newLineList = lineList.map((block, index) => {
          if (index > lineIndex) return block;

          if (index < lineIndex) {
            return {
              ...block,
              highlightList: [],
            };
          }
          return {
            ...block,
            highlightList: getRemovedHighlightList({
              blockTextLength: block.text.length,
              highlightList: block.highlightList,
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

        const newLineList: EditorLine[] = targetAnswer.lineList.map((block) => ({
          ...block,
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
  const handleClickBlockList = (event: React.MouseEvent) => {
    if (!isEditAble) return;

    const isSameSelectedNode = isSingleCharacterSelected();

    if (isSameSelectedNode) return;

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

    setRemovalTarget({
      answerId: targetAnswer.answerId,
      lineIndex: Number(lineIndex),
      highlightIndex: Number(highlightIndex),
    });

    updateRemoverPosition(rect);
  };

  const removeHighlightByClick = async () => {
    if (!removalTarget) return;

    const { answerId, lineIndex, highlightIndex } = removalTarget;

    const newEditorAnswerMap = new Map(editorAnswerMap);
    const targetAnswer = newEditorAnswerMap.get(answerId);
    if (!targetAnswer) return;

    const newLineList = [...targetAnswer.lineList];
    const targetBlock = newLineList[lineIndex];
    const newHighlightList = [...targetBlock.highlightList];

    newHighlightList.splice(highlightIndex, 1);
    const newTargetBlock: EditorLine = { ...targetBlock, highlightList: newHighlightList };

    newLineList.splice(lineIndex, 1, newTargetBlock);
    newEditorAnswerMap.set(answerId, { ...targetAnswer, lineList: newLineList });

    // TODO: 서버에 API 요청 보내고 성공 한 후 상태 업데이트
    setEditorAnswerMap(newEditorAnswerMap);

    // 초기화
    hideRemover();
    setRemovalTarget(null);
  };

  return {
    editorAnswerMap,
    addHighlight,
    removeHighlightByDrag,
    handleClickBlockList,
    removeHighlightByClick,
    removalTarget,
  };
};

export default useHighlight;
