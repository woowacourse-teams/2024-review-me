import { useState } from 'react';

import { SelectionInfo } from '@/utils';

const useHighlightButtonPosition = () => {
  interface Position {
    top: number;
    left: number;
  }

  const [highlightButtonPosition, setHighlightButtonPosition] = useState<Position | null>(null);
  const hideHighlightButton = () => setHighlightButtonPosition(null);

  const calculateEndPosition = ({ selection, isForwardDrag, startBlock }: SelectionInfo) => {
    const range = selection.getRangeAt(0);
    const rects = range.getClientRects();

    if (rects.length === 0) return;

    // 드래그 방향에 따른 마지막 rect의 좌표 정보를 가져옴 (마우스가 놓인 최종 지점)
    const lastRect = rects[isForwardDrag ? rects.length - 1 : 0];
    const GAP_WIDTH_SELECTION = 10;
    const endPosition = {
      left: isForwardDrag ? lastRect.right + GAP_WIDTH_SELECTION : lastRect.left - GAP_WIDTH_SELECTION,
      top: lastRect.top - (isForwardDrag ? 0 : startBlock.clientHeight),
    };

    return endPosition;
  };

  const updateHighlightButtonPosition = (info: SelectionInfo) => {
    const endPosition = calculateEndPosition(info);
    if (!endPosition) return console.error('endPosition을 찾을 수 없어요.');

    setHighlightButtonPosition(endPosition);
  };

  return {
    highlightButtonPosition,
    hideHighlightButton,
    updateHighlightButtonPosition,
  };
};

export default useHighlightButtonPosition;
