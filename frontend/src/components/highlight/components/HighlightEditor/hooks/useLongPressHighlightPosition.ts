import { useLayoutEffect } from 'react';

import { GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON, HIGHLIGHT_MENU_STYLE_SIZE } from '@/constants';
import { Position } from '@/types';

import { useLongPressHighlightButtonPosition } from '.';

interface UseLongPressHighlightPositionProps {
  isEditable: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
  updateHighlightMenuPosition: (position: Position | null) => void;
}
const useLongPressHighlightPosition = ({
  isEditable,
  editorRef,
  updateHighlightMenuPosition,
}: UseLongPressHighlightPositionProps) => {
  const isOverflowingEditor = (longPressTargetRect: DOMRect, editorRect: DOMRect) => {
    const buttonTotalHeight = HIGHLIGHT_MENU_STYLE_SIZE.height + HIGHLIGHT_MENU_STYLE_SIZE.shadow;

    const isOverflowingVertically = {
      top: longPressTargetRect.top - GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON - buttonTotalHeight <= editorRect.top,
      bottom:
        longPressTargetRect.bottom + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON + buttonTotalHeight >= editorRect.bottom,
    };

    return { isOverflowingVertically };
  };

  const calculateHighlightMenuPositionByLongPress = (longPressTargetRect: DOMRect, editorRect: DOMRect) => {
    const buttonRectTop = longPressTargetRect.bottom + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON;
    const buttonTotalHeight = HIGHLIGHT_MENU_STYLE_SIZE.height + HIGHLIGHT_MENU_STYLE_SIZE.shadow;

    const { isOverflowingVertically } = isOverflowingEditor(longPressTargetRect, editorRect);

    let topOffsetFromParent = buttonRectTop - editorRect.top;

    if (isOverflowingVertically.bottom) {
      topOffsetFromParent =
        longPressTargetRect.top - GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON - buttonTotalHeight - editorRect.top;
    }

    if (isOverflowingVertically.top) {
      topOffsetFromParent = HIGHLIGHT_MENU_STYLE_SIZE.shadow;
    }

    // 하이아리트 영역의 중간에 위치
    const leftOffsetFromParent = longPressTargetRect.left + longPressTargetRect.width / 2 - editorRect.left;

    return { topOffsetFromParent, leftOffsetFromParent };
  };

  const updateHighlightMenuPositionByLongPress = (longPressTargetRect: DOMRect) => {
    const editorRect = editorRef.current?.getClientRects()[0];
    if (!editorRect) return;

    const { topOffsetFromParent, leftOffsetFromParent } = calculateHighlightMenuPositionByLongPress(
      longPressTargetRect,
      editorRect,
    );

    updateHighlightMenuPosition({
      top: `
      ${topOffsetFromParent / 10}rem`,
      left: `${leftOffsetFromParent / 10}rem`,
    });
  };

  useLayoutEffect(() => {
    if (!isEditable) updateHighlightMenuPosition(null);
  }, [isEditable]);

  return {
    updateHighlightMenuPositionByLongPress,
  };
};

export default useLongPressHighlightPosition;

export type UseLongPressHighlightPositionReturn = ReturnType<typeof useLongPressHighlightButtonPosition>;
