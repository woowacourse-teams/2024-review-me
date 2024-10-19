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
  const updateHighlightMenuPositionByLongPress = (rect: DOMRect) => {
    const editorRect = editorRef.current?.getClientRects()[0];
    if (!editorRect) return;

    const top = rect.bottom + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON;
    const left = rect.left + rect.width / 2 - editorRect.left;

    const buttonTotalHeight = HIGHLIGHT_MENU_STYLE_SIZE.height + HIGHLIGHT_MENU_STYLE_SIZE.shadow;
    const isOverflowingVertically =
      rect.bottom + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON + buttonTotalHeight >= editorRect.bottom;

    const topOffsetFromParent =
      (isOverflowingVertically ? rect.top - GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON - buttonTotalHeight : top) -
      editorRect.top;

    const leftOffsetFromParent = left;

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
