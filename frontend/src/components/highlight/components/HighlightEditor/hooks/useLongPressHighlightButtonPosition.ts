import { useLayoutEffect, useState } from 'react';

import { GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON, HIGHLIGHT_BUTTON_SIZE } from '@/constants';
import { Position } from '@/types';

interface UseLongPressHighlightButtonPositionProps {
  isEditable: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}
const useLongPressHighlightButtonPosition = ({ isEditable, editorRef }: UseLongPressHighlightButtonPositionProps) => {
  const [longPressHighlightButtonPosition, setLongPressHighlightButtonPosition] = useState<Position | null>(null);

  const updateLongPressHighlightButtonPosition = (rect: DOMRect) => {
    const editorRect = editorRef.current?.getClientRects()[0];
    if (!editorRect) return;

    const top = rect.bottom + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON;
    const left = rect.left + rect.width / 2 - editorRect.left;

    const buttonTotalHeight = HIGHLIGHT_BUTTON_SIZE.height + HIGHLIGHT_BUTTON_SIZE.shadow;
    const isOverflowingVertically =
      rect.bottom + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON + buttonTotalHeight >= editorRect.bottom;

    const topOffsetFromParent =
      (isOverflowingVertically ? rect.top - GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON - buttonTotalHeight : top) -
      editorRect.top;

    const leftOffsetFromParent = left;

    setLongPressHighlightButtonPosition({
      top: `
      ${topOffsetFromParent / 10}rem`,
      left: `${leftOffsetFromParent / 10}rem`,
    });
  };

  const hideLongPressHighlightButton = () => setLongPressHighlightButtonPosition(null);

  useLayoutEffect(() => {
    if (!isEditable) hideLongPressHighlightButton();
  }, [isEditable]);

  return {
    longPressHighlightButtonPosition,
    updateLongPressHighlightButtonPosition,
    hideLongPressHighlightButton,
  };
};

export default useLongPressHighlightButtonPosition;
