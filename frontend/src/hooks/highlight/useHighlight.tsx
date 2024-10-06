import { useState } from 'react';

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
  text: string;
  hideHighlightButton: () => void;
}
const useHighlight = ({ text, hideHighlightButton }: UseHighlightProps) => {
  const [blockList, setBlockList] = useState<EditorBlockData[]>(() =>
    text.split('\n').map((text) => ({
      text,
      highlightList: [],
    })),
  );

  const handleClickHighlight = () => {
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
    hideHighlightButton();
  };

  const handleClickHighlightRemover = () => {
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
    hideHighlightButton();
  };

  return {
    blockList,
    handleClickHighlight,
    handleClickHighlightRemover,
  };
};

export default useHighlight;
