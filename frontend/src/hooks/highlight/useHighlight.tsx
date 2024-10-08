import { useLayoutEffect, useState } from 'react';

import { EDITOR_ANSWER_CLASS_NAME, HIGHLIGHT_SPAN_CLASS_NAME } from '@/constants';
import { EditorBlockData } from '@/types';
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
  answerList: { id: number; text: string }[];
  isAbleEdit: boolean;
  hideHighlightToggleButton: () => void;
  updateRemoverPosition: (rect: DOMRect) => void;
  hideRemover: () => void;
}

interface RemovalTarget {
  answerId: number;
  blockIndex: number;
  highlightIndex: number;
}

const useHighlight = ({
  answerList,
  isAbleEdit,
  hideHighlightToggleButton,
  updateRemoverPosition,
  hideRemover,
}: UseHighlightProps) => {
  interface Answer {
    id: number;
    index: number;
    blockList: EditorBlockData[];
  }
  type AnswerMap = Map<number, Answer>;
  const [answerMap, setAnswerMap] = useState<AnswerMap>(new Map());

  const makeBlockListByText = (answer: string) => {
    return answer.split('\n').map((text) => ({
      text,
      highlightList: [],
    }));
  };

  useLayoutEffect(() => {
    const map: AnswerMap = new Map();

    answerList.forEach(({ id, text }, index) => {
      map.set(id, { id, blockList: makeBlockListByText(text), index });
    });

    setAnswerMap(map);
  }, [answerList]);

  // span 클릭 시, 제공되는 형광펜 삭제 기능 타겟
  const [removalTarget, setRemovalTarget] = useState<RemovalTarget | null>(null);

  const addHighlight = () => {
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    selectionInfo.isSameAnswer ? addSingleAnswerHighlight(selectionInfo) : addMultipleAnswerHighlight(selectionInfo);

    removeSelection();
    hideHighlightToggleButton();
  };

  const addMultipleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
    const { startAnswer, endAnswer } = selectionInfo;
    const newAnswerMap = new Map(answerMap);
    if (!startAnswer || !endAnswer) return;

    newAnswerMap.keys().forEach((answerId, answerIndex) => {
      if (startAnswer.id === answerId) {
        const { blockIndex, offset } = startAnswer;
        const targetAnswer = newAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { blockList } = targetAnswer;

        const newBlockList = blockList.map((block, index) => {
          if (index < blockIndex) return block;

          if (index > blockIndex) {
            return {
              ...block,
              highlightList: [{ start: 0, end: block.text.length - 1 }],
            };
          }
          return getUpdatedBlockByHighlight({
            blockTextLength: block.text.length,
            blockIndex: index,
            start: offset,
            end: block.text.length - 1,
            blockList,
          });
        });

        newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }

      if (startAnswer.index < answerIndex && endAnswer.index > answerIndex) {
        const targetAnswer = newAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { blockList } = targetAnswer;

        const newBlockList = blockList.map((block) => ({
          ...block,
          highlightList: [{ start: 0, end: block.text.length - 1 }],
        }));

        newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }

      if (endAnswer.id === answerId) {
        const { blockIndex, offset } = endAnswer;
        const targetAnswer = newAnswerMap.get(answerId);

        if (!targetAnswer) return;
        const { blockList } = targetAnswer;

        const newBlockList = blockList.map((block, index) => {
          if (index > blockIndex) return block;
          if (index < blockIndex) {
            return {
              ...block,
              highlightList: [{ start: 0, end: block.text.length - 1 }],
            };
          }

          return getUpdatedBlockByHighlight({
            blockTextLength: block.text.length,
            blockIndex: index,
            start: 0,
            end: offset,
            blockList,
          });
        });

        newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }
    });

    setAnswerMap(newAnswerMap);
  };

  const addSingleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
    const { startBlockIndex, endBlockIndex, startAnswer } = selectionInfo;
    if (!startAnswer) return;

    const newAnswerMap = new Map(answerMap);
    const answerId = startAnswer.id;
    const targetAnswer = newAnswerMap.get(answerId);

    if (!targetAnswer) return;

    const newBlockList = targetAnswer.blockList.map((block, index, array) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const { start, end } = getStartBlockOffset(selectionInfo, block);

        return getUpdatedBlockByHighlight({
          blockTextLength: block.text.length,
          blockIndex: index,
          start,
          end,
          blockList: array,
        });
      }

      if (index === endBlockIndex) {
        const end = getEndBlockOffset(selectionInfo);

        return getUpdatedBlockByHighlight({
          blockTextLength: block.text.length,
          blockIndex: index,
          start: 0,
          end,
          blockList: array,
        });
      }
      return {
        ...block,
        highlightList: [{ start: 0, end: block.text.length }],
      };
    });

    newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
    setAnswerMap(newAnswerMap);
    removeSelection();
    hideHighlightToggleButton();
  };

  const removeHighlight = () => {
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    selectionInfo.isSameAnswer
      ? removeSingleAnswerHighlight(selectionInfo)
      : removeMultipleAnswerHighlight(selectionInfo);
    removeSelection();
    hideHighlightToggleButton();
  };

  const removeSingleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
    const { startBlockIndex, endBlockIndex, startAnswer } = selectionInfo;
    if (!startAnswer) return;

    const newAnswerMap = new Map(answerMap);
    const answerId = startAnswer.id;
    const targetAnswer = newAnswerMap.get(answerId);

    if (!targetAnswer) return;

    const newBlockList = targetAnswer.blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const { start, end } = getStartBlockOffset(selectionInfo, block);

        return {
          ...block,
          highlightList: getRemovedHighlightList({
            blockTextLength: block.text.length,
            highlightList: block.highlightList,
            start,
            end,
          }),
        };
      }
      if (index === endBlockIndex) {
        const end = getEndBlockOffset(selectionInfo);
        return {
          ...block,
          highlightList: getRemovedHighlightList({
            blockTextLength: block.text.length,
            highlightList: block.highlightList,
            start: 0,
            end,
          }),
        };
      }
      return {
        ...block,
        highlightList: [],
      };
    });

    newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
    setAnswerMap(newAnswerMap);
  };

  const removeMultipleAnswerHighlight = (selectionInfo: EditorSelectionInfo) => {
    const { startAnswer, endAnswer } = selectionInfo;
    const newAnswerMap = new Map(answerMap);
    if (!startAnswer || !endAnswer) return;

    newAnswerMap.keys().forEach((answerId, answerIndex) => {
      if (answerId === startAnswer.id) {
        const { blockIndex, offset } = startAnswer;
        const targetAnswer = newAnswerMap.get(answerId);

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
              start: offset,
              end: block.text.length - 1,
            }),
          };
        });

        newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }
      if (answerId === endAnswer.id) {
        const { blockIndex, offset } = endAnswer;
        const targetAnswer = newAnswerMap.get(answerId);

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
              start: 0,
              end: offset,
            }),
          };
        });

        newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }

      if (answerIndex > startAnswer.index && answerIndex < endAnswer.index) {
        const targetAnswer = newAnswerMap.get(answerId);
        if (!targetAnswer) return;

        const newBlockList: EditorBlockData[] = targetAnswer.blockList.map((block) => ({
          ...block,
          highlightList: [],
        }));
        newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });
      }

      setAnswerMap(newAnswerMap);
    });
  };

  const handleClickBlockList = (event: React.MouseEvent) => {
    if (!isAbleEdit) return;
    const target = event.target as HTMLElement;
    if (!target.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME)) return;

    const answerElement = target.closest(`.${EDITOR_ANSWER_CLASS_NAME}`);
    if (!answerElement) return;
    const id = answerElement.getAttribute('data-answer')?.split('-')[0];
    if (!id) return;
    console.log(id);
    const targetAnswer = answerMap.get(Number(id));
    if (!targetAnswer) return;

    const rect = target.getClientRects()[0];
    if (!target.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME)) return;
    const blockIndex = target.parentElement?.getAttribute('data-index');
    const start = target.getAttribute('data-highlight-start');
    const end = target.getAttribute('data-highlight-end');
    if (!blockIndex || !start || !end) return;
    const { highlightList } = targetAnswer.blockList[Number(blockIndex)];
    const highlightIndex = highlightList.findIndex((i) => i.start === Number(start) && i.end === Number(end));

    setRemovalTarget({
      answerId: targetAnswer.id,
      blockIndex: Number(blockIndex),
      highlightIndex: Number(highlightIndex),
    });

    updateRemoverPosition(rect);
  };

  const handleClickRemover = () => {
    if (!removalTarget) return;

    const { answerId, blockIndex, highlightIndex } = removalTarget;

    const newAnswerMap = new Map(answerMap);
    const targetAnswer = newAnswerMap.get(answerId);
    if (!targetAnswer) return;

    const newBlockList = [...targetAnswer.blockList];
    const targetBlock = newBlockList[blockIndex];
    const newHighlightList = [...targetBlock.highlightList];

    newHighlightList.splice(highlightIndex, 1);
    const newTargetBlock: EditorBlockData = { ...targetBlock, highlightList: newHighlightList };

    newBlockList.splice(blockIndex, 1, newTargetBlock);
    newAnswerMap.set(answerId, { ...targetAnswer, blockList: newBlockList });

    setAnswerMap(newAnswerMap);
    hideRemover();
    setRemovalTarget(null);
  };

  return {
    answerMap,
    addHighlight,
    removeHighlight,
    handleClickBlockList,
    handleClickRemover,
    removalTarget,
  };
};

export default useHighlight;
