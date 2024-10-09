import { useState } from 'react';

import { EDITOR_ANSWER_CLASS_NAME, HIGHLIGHT_SPAN_CLASS_NAME } from '@/constants';
import { EditorAnswerData, EditorAnswerMap, EditorBlockData, HighlightData } from '@/types';
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
  answerList: EditorAnswerData[];
  isEditAble: boolean;
  hideHighlightToggleButton: () => void;
  updateRemoverPosition: (rect: DOMRect) => void;
  hideRemover: () => void;
}

interface RemovalTarget {
  answerId: number;
  blockIndex: number;
  highlightIndex: number;
}

const findBlockHighlightListFromAnswer = (answerHighlightList: HighlightData[], blockIndex: number) => {
  return answerHighlightList.find((i) => i.lineIndex === blockIndex)?.rangeList || [];
};
const makeBlockListByText = (content: string, answerHighlightList: HighlightData[]): EditorBlockData[] => {
  return content.split('\n').map((text, index) => ({
    text,
    highlightList: findBlockHighlightListFromAnswer(answerHighlightList, index),
  }));
};

const makeInitialEditorAnswerMap = (answerList: EditorAnswerData[]) => {
  const initialEditorAnswerMap: EditorAnswerMap = new Map();

  answerList.forEach((answer, index) => {
    initialEditorAnswerMap.set(answer.answerId, {
      ...answer,
      answerIndex: index,
      blockList: makeBlockListByText(answer.content, answer.highlightList),
    });
  });

  return initialEditorAnswerMap;
};

const useHighlight = ({
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

  const addHighlight = () => {
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;
    const newAnswerMap = selectionInfo.isSameAnswer
      ? addSingleAnswerHighlight(selectionInfo)
      : addMultipleAnswerHighlight(selectionInfo);
    if (!newAnswerMap) return;
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
        const { blockIndex, offset } = startAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { blockList } = targetAnswer;

        const newBlockList: EditorBlockData[] = blockList.map((block, index) => {
          if (index < blockIndex) return block;

          if (index > blockIndex) {
            return {
              ...block,
              highlightList: [{ startIndex: 0, endIndex: block.text.length - 1 }],
            };
          }
          return getUpdatedBlockByHighlight({
            blockTextLength: block.text.length,
            blockIndex: index,
            startIndex: offset,
            endIndex: block.text.length - 1,
            blockList,
          });
        });

        newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }

      if (startAnswer.index < answerIndex && endAnswer.index > answerIndex) {
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { blockList } = targetAnswer;

        const newBlockList = blockList.map((block) => ({
          ...block,
          highlightList: [{ startIndex: 0, endIndex: block.text.length - 1 }],
        }));

        newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }

      if (endAnswer.id === answerId) {
        const { blockIndex, offset } = endAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { blockList } = targetAnswer;

        const newBlockList = blockList.map((block, index) => {
          if (index > blockIndex) return block;
          if (index < blockIndex) {
            return {
              ...block,
              highlightList: [{ startIndex: 0, endIndex: block.text.length - 1 }],
            };
          }

          return getUpdatedBlockByHighlight({
            blockTextLength: block.text.length,
            blockIndex: index,
            startIndex: 0,
            endIndex: offset,
            blockList,
          });
        });

        newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
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

    const newBlockList: EditorBlockData[] = targetAnswer.blockList.map((block, index, array) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const { startIndex, endIndex } = getStartBlockOffset(selectionInfo, block);

        return getUpdatedBlockByHighlight({
          blockTextLength: block.text.length,
          blockIndex: index,
          startIndex,
          endIndex,
          blockList: array,
        });
      }

      if (index === endBlockIndex) {
        const endIndex = getEndBlockOffset(selectionInfo);

        return getUpdatedBlockByHighlight({
          blockTextLength: block.text.length,
          blockIndex: index,
          startIndex: 0,
          endIndex,
          blockList: array,
        });
      }
      return {
        ...block,
        highlightList: [{ startIndex: 0, endIndex: block.text.length }],
      };
    });

    newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
    return newEditorAnswerMap;
  };

  const removeHighlight = () => {
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

    const newBlockList = targetAnswer.blockList.map((block, index) => {
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

    newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
    return newEditorAnswerMap;
  };

  const removeMultipleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
    const { startAnswer, endAnswer } = selectionInfo;
    const newEditorAnswerMap = new Map(editorAnswerMap);
    if (!startAnswer || !endAnswer) return;

    [...newEditorAnswerMap.keys()].forEach((answerId, answerIndex) => {
      if (answerId === startAnswer.id) {
        const { blockIndex, offset } = startAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { blockList } = targetAnswer;

        const newBlockList = blockList.map((block, index) => {
          if (index < blockIndex) return block;

          if (index > blockIndex) {
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

        newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }
      if (answerId === endAnswer.id) {
        const { blockIndex, offset } = endAnswer;
        const targetAnswer = newEditorAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { blockList } = targetAnswer;

        const newBlockList = blockList.map((block, index) => {
          if (index > blockIndex) return block;

          if (index < blockIndex) {
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

        newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }

      if (answerIndex > startAnswer.index && answerIndex < endAnswer.index) {
        const targetAnswer = newEditorAnswerMap.get(answerId);
        if (!targetAnswer) return;

        const newBlockList: EditorBlockData[] = targetAnswer.blockList.map((block) => ({
          ...block,
          highlightList: [],
        }));
        newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
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
    const blockIndex = target.parentElement?.getAttribute('data-index');
    const start = target.getAttribute('data-highlight-start');
    const end = target.getAttribute('data-highlight-end');
    if (!blockIndex || !start || !end) return;
    const { highlightList } = targetAnswer.blockList[Number(blockIndex)];
    const highlightIndex = highlightList.findIndex((i) => i.startIndex === Number(start) && i.endIndex === Number(end));

    setRemovalTarget({
      answerId: targetAnswer.answerId,
      blockIndex: Number(blockIndex),
      highlightIndex: Number(highlightIndex),
    });

    updateRemoverPosition(rect);
  };

  const handleClickRemover = () => {
    if (!removalTarget) return;

    const { answerId, blockIndex, highlightIndex } = removalTarget;

    const newEditorAnswerMap = new Map(editorAnswerMap);
    const targetAnswer = newEditorAnswerMap.get(answerId);
    if (!targetAnswer) return;

    const newBlockList = [...targetAnswer.blockList];
    const targetBlock = newBlockList[blockIndex];
    const newHighlightList = [...targetBlock.highlightList];

    newHighlightList.splice(highlightIndex, 1);
    const newTargetBlock: EditorBlockData = { ...targetBlock, highlightList: newHighlightList };

    newBlockList.splice(blockIndex, 1, newTargetBlock);
    newEditorAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });

    // TODO: 서버에 API 요청 보내고 성공 한 후 상태 업데이트
    setEditorAnswerMap(newEditorAnswerMap);

    // 초기화
    hideRemover();
    setRemovalTarget(null);
  };

  return {
    editorAnswerMap,
    addHighlight,
    removeHighlight,
    handleClickBlockList,
    handleClickRemover,
    removalTarget,
  };
};

export default useHighlight;
