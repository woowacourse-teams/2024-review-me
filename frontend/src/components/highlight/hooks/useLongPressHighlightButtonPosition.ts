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
    const top = rect.bottom - editorRect.top;
    const left = rect.right - editorRect.left;

    const buttonWidth = HIGHLIGHT_BUTTON_SIZE.width.basic;

    const isOverEditorArea = editorRect.right < rect.right + buttonWidth;
    const topOffsetFromParent = isOverEditorArea ? top + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON : top;
    const leftOffsetFromParent = isOverEditorArea ? editorRect.width - buttonWidth : left;

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
