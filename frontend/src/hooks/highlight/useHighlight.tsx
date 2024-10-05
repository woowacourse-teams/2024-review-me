import { useState } from 'react';

import { EditorBlock } from '@/types';
import {
  getRemovedHighlightList,
  getSelectionInfo,
  getSelectionOffsetInBlock,
  getUpdatedBlockByHighlight,
  removeSelection,
} from '@/utils';

interface UseHighlightProps {
  text: string;
}
const useHighlight = ({ text }: UseHighlightProps) => {
  const [blockList, setBlockList] = useState<EditorBlock[]>(() =>
    text.split('\n').map((text) => ({
      text,
      highlightList: [],
    })),
  );

  const handleClickHighlight = () => {
    const info = getSelectionInfo();
    if (!info) return;
    const { selection, startBlock, startBlockIndex, endBlock, endBlockIndex } = info;

    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const startOffset = getSelectionOffsetInBlock({ selection, blockElement: startBlock });
        return getUpdatedBlockByHighlight({ blockIndex: index, ...startOffset, blockList });
      }
      if (index === endBlockIndex) {
        const endOffset = getSelectionOffsetInBlock({ selection, blockElement: endBlock });
        return getUpdatedBlockByHighlight({ blockIndex: index, start: 0, end: endOffset.start, blockList });
      }
      return {
        ...block,
        highlightList: [{ start: 0, length: block.text.length }],
      };
    });

    setBlockList(newBlockList);
    removeSelection();
  };

  const handleClickHighlightRemover = () => {
    const info = getSelectionInfo();
    if (!info) return;
    const { selection, startBlock, startBlockIndex, endBlock, endBlockIndex } = info;

    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const startOffset = getSelectionOffsetInBlock({ selection, blockElement: startBlock });
        return {
          ...block,
          highlightList: getRemovedHighlightList({ highlightList: block.highlightList, ...startOffset }),
        };
      }
      if (index === endBlockIndex) {
        const endOffset = getSelectionOffsetInBlock({ selection, blockElement: endBlock });
        return {
          ...block,
          highlightList: getRemovedHighlightList({
            highlightList: block.highlightList,
            start: 0,
            end: endOffset.start,
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
  };

  return {
    blockList,
    handleClickHighlight,
    handleClickHighlightRemover,
  };
};

export default useHighlight;
