import { useState } from 'react';

import { EditorBlockData } from '@/types';
import {
  getRemovedHighlightList,
  getSelectionInfo,
  getSelectionOffsetInBlock,
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
    const info = getSelectionInfo();
    if (!info) return;
    const { selection, startBlock, startBlockIndex, endBlock, endBlockIndex } = info;
    const isOnlyOneSelectedBlock = startBlockIndex === endBlockIndex;
    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const startOffset = getSelectionOffsetInBlock({
          selection,
          blockElement: startBlock,
          isOnlyOneSelectedBlock,
          isStartBlock: true,
        });

        return getUpdatedBlockByHighlight({
          blockTextLength: text.length,
          blockIndex: index,
          ...startOffset,
          blockList,
        });
      }
      if (index === endBlockIndex) {
        const endOffset = getSelectionOffsetInBlock({
          selection,
          blockElement: endBlock,
          isOnlyOneSelectedBlock,
          isStartBlock: false,
        });

        return getUpdatedBlockByHighlight({
          blockTextLength: text.length,
          blockIndex: index,
          start: 0,
          end: endOffset.end,
          blockList,
        });
      }
      return {
        ...block,
        highlightList: [{ start: 0, end: block.text.length - 1 }],
      };
    });
    setBlockList(newBlockList);
    removeSelection();
    hideHighlightButton();
  };

  const handleClickHighlightRemover = () => {
    const info = getSelectionInfo();
    if (!info) return;
    const { selection, startBlock, startBlockIndex, endBlock, endBlockIndex } = info;
    const isOnlyOneSelectedBlock = startBlockIndex === endBlockIndex;
    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const startOffset = getSelectionOffsetInBlock({
          selection,
          blockElement: startBlock,
          isOnlyOneSelectedBlock,
          isStartBlock: true,
        });
        return {
          ...block,
          highlightList: getRemovedHighlightList({
            blockTextLength: text.length,
            highlightList: block.highlightList,
            ...startOffset,
          }),
        };
      }
      if (index === endBlockIndex) {
        const endOffset = getSelectionOffsetInBlock({
          selection,
          blockElement: endBlock,
          isOnlyOneSelectedBlock,
          isStartBlock: false,
        });
        return {
          ...block,
          highlightList: getRemovedHighlightList({
            blockTextLength: text.length,
            highlightList: block.highlightList,
            start: 0,
            end: endOffset.end,
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
