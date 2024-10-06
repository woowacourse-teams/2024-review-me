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
    const { selection, startBlock, startBlockIndex, endBlock, endBlockIndex, isForwardDrag } = info;
    const { anchorNode, anchorOffset, focusNode, focusOffset } = selection;
    const isOnlyOneSelectedBlock = startBlockIndex === endBlockIndex;
    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const start = getSelectionOffsetInBlock({
          selectionTargetNode: isForwardDrag ? anchorNode : focusNode,
          selectionTargetOffset: isForwardDrag ? anchorOffset : focusOffset,
          blockElement: startBlock,
        });
        // NOTE: end에 -1하는 이유 : 끝나는 포커스위치의 offset이 글자 index보다 1큼
        const end = isOnlyOneSelectedBlock
          ? getSelectionOffsetInBlock({
              selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
              selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
              blockElement: startBlock,
            })
          : block.text.length;

        return getUpdatedBlockByHighlight({
          blockTextLength: text.length,
          blockIndex: index,
          start,
          end,
          blockList,
        });
      }
      if (index === endBlockIndex) {
        const end = getSelectionOffsetInBlock({
          selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
          selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
          blockElement: endBlock,
        });

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
    const info = getSelectionInfo();
    if (!info) return;
    const { selection, startBlock, startBlockIndex, endBlock, endBlockIndex, isForwardDrag } = info;
    const { anchorNode, anchorOffset, focusNode, focusOffset } = selection;
    const isOnlyOneSelectedBlock = startBlockIndex === endBlockIndex;
    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const start = getSelectionOffsetInBlock({
          selectionTargetNode: isForwardDrag ? anchorNode : focusNode,
          selectionTargetOffset: isForwardDrag ? anchorOffset : focusOffset,
          blockElement: startBlock,
        });

        const end = isOnlyOneSelectedBlock
          ? getSelectionOffsetInBlock({
              selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
              selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
              blockElement: startBlock,
            })
          : block.text.length;

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
        const end = getSelectionOffsetInBlock({
          selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
          selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
          blockElement: endBlock,
        });
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
