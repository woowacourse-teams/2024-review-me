import { useLayoutEffect, useState } from 'react';

import { Position } from '@/types';
import { EditorSelectionInfo } from '@/utils';

interface UseHighlightButtonPositionProps {
  isEditAble: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}

const useHighlightToggleButtonPosition = ({ isEditAble, editorRef }: UseHighlightButtonPositionProps) => {
  const [highlightToggleButtonPosition, setHighlightToggleButtonPosition] = useState<Position | null>(null);

  const hideHighlightToggleButton = () => setHighlightToggleButtonPosition(null);

  const calculateEndPosition = ({ selection, isForwardDrag, startBlock }: EditorSelectionInfo) => {
    if (!editorRef.current) return;
    const range = selection.getRangeAt(0);
    const rects = range.getClientRects();
    const editorRect = editorRef.current.getClientRects()[0];

    if (rects.length === 0) return;

    // 드래그 방향에 따른 마지막 rect의 좌표 정보를 가져옴 (마우스가 놓인 최종 지점)
    const lastRect = rects[isForwardDrag ? rects.length - 1 : 0];

    const GAP_WIDTH_SELECTION = 10;
    const endPosition = {
      left: isForwardDrag
        ? lastRect.right + GAP_WIDTH_SELECTION - editorRect.left
        : lastRect.left - GAP_WIDTH_SELECTION - editorRect.left,
      top: lastRect.top - (isForwardDrag ? 0 : startBlock.clientHeight) - editorRect.top,
    };

    return endPosition;
  };

  const updateHighlightToggleButtonPosition = (info: EditorSelectionInfo) => {
    const endPosition = calculateEndPosition(info);
    if (!endPosition) return console.error('endPosition을 찾을 수 없어요.');

    setHighlightToggleButtonPosition(endPosition);
  };

  useLayoutEffect(() => {
    if (!isEditAble) hideHighlightToggleButton();
  }, [isEditAble]);

  useLayoutEffect(() => {});

  return {
    highlightToggleButtonPosition,
    hideHighlightToggleButton,
    updateHighlightToggleButtonPosition,
  };
};

export default useHighlightToggleButtonPosition;
