import { useState } from 'react';

import { HIGHLIGHT_SPAN_CLASS_NAME } from '@/constants';
import { EditorBlockData } from '@/types';
import {
  getEndBlockOffset,
  getStartBlockOffset,
  getRemovedHighlightList,
  findSelectionInfo,
  getUpdatedBlockByHighlight,
  removeSelection,
} from '@/utils';

interface UseHighlightProps {
  isAbleEdit: boolean;
  text: string;
  hideHighlightToggleButton: () => void;
  updateRemoverPosition: (rect: DOMRect) => void;
  hideRemover: () => void;
}

interface RemovalTarget {
  blockIndex: number;
  highlightIndex: number;
}

const useHighlight = ({
  isAbleEdit,
  text,
  hideHighlightToggleButton,
  updateRemoverPosition,
  hideRemover,
}: UseHighlightProps) => {
  const [blockList, setBlockList] = useState<EditorBlockData[]>(() =>
    text.split('\n').map((text) => ({
      text,
      highlightList: [],
    })),
  );
  // span 클릭 시, 제공되는 형광펜 삭제 기능 타겟
  const [removalTarget, setRemovalTarget] = useState<RemovalTarget | null>(null);

  const addHighlight = () => {
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    const { startBlockIndex, endBlockIndex } = selectionInfo;

    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const { start, end } = getStartBlockOffset(selectionInfo, block);

        return getUpdatedBlockByHighlight({
          blockTextLength: text.length,
          blockIndex: index,
          start,
          end,
          blockList,
        });
      }

      if (index === endBlockIndex) {
        const end = getEndBlockOffset(selectionInfo);

        return getUpdatedBlockByHighlight({
          blockTextLength: text.length,
          blockIndex: index,
          start: 0,
          end,
          blockList,
        });
      }
      return {
        ...block,
        highlightList: [{ start: 0, end: block.text.length }],
      };
    });
    setBlockList(newBlockList);
    removeSelection();
    hideHighlightToggleButton();
  };

  const removeHighlight = () => {
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    const { startBlockIndex, endBlockIndex } = selectionInfo;

    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const { start, end } = getStartBlockOffset(selectionInfo, block);

        return {
          ...block,
          highlightList: getRemovedHighlightList({
            blockTextLength: text.length,
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
            blockTextLength: text.length,
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

    setBlockList(newBlockList);
    removeSelection();
    hideHighlightToggleButton();
  };

  const handleClickBlockList = (event: React.MouseEvent) => {
    if (!document.getSelection()?.isCollapsed) return;
    if (!isAbleEdit) return;

    const target = event.target as HTMLElement;
    const rect = target.getClientRects()[0];

    if (!target.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME)) return;
    const blockIndex = target.parentElement?.getAttribute('data-index');
    const start = target.getAttribute('data-highlight-start');
    const end = target.getAttribute('data-highlight-end');

    if (!blockIndex || !start || !end) return;
    const { highlightList } = blockList[Number(blockIndex)];
    const highlightIndex = highlightList.findIndex((i) => i.start === Number(start) && i.end === Number(end));

    setRemovalTarget({ blockIndex: Number(blockIndex), highlightIndex: Number(highlightIndex) });

    updateRemoverPosition(rect);
  };

  const handleClickRemover = () => {
    if (!removalTarget) return;

    const { blockIndex, highlightIndex } = removalTarget;
    const newBlockList = [...blockList];
    const targetBlock = newBlockList[blockIndex];
    const newHighlightList = [...targetBlock.highlightList];

    newHighlightList.splice(highlightIndex, 1);
    const newTargetBlock: EditorBlockData = { ...targetBlock, highlightList: newHighlightList };

    newBlockList.splice(blockIndex, 1, newTargetBlock);
    setBlockList(newBlockList);

    hideRemover();
    setRemovalTarget(null);
  };

  return {
    blockList,
    setBlockList,
    addHighlight,
    removeHighlight,
    handleClickBlockList,
    handleClickRemover,
    removalTarget,
  };
};

export default useHighlight;
