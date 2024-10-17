import { useLayoutEffect, useState } from 'react';

import { GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON, HIGHLIGHT_BUTTON_SIZE } from '@/constants';
import { Position } from '@/types';

interface UseHighlightRemoverPositionProps {
  isEditable: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}
const useHighlightRemoverPosition = ({ isEditable, editorRef }: UseHighlightRemoverPositionProps) => {
  const [removerPosition, setRemoverPosition] = useState<Position | null>(null);

  const updateRemoverPosition = (rect: DOMRect) => {
    const editorRect = editorRef.current?.getClientRects()[0];
    if (!editorRect) return;
    const top = rect.bottom - editorRect.top;
    const left = rect.right - editorRect.left;

    const buttonWidth = HIGHLIGHT_BUTTON_SIZE.width.basic;

    const isOverEditorArea = editorRect.right < rect.right + buttonWidth;
    const topOffsetFromParent = isOverEditorArea ? top + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON : top;
    const leftOffsetFromParent = isOverEditorArea ? editorRect.width - buttonWidth : left;

    setRemoverPosition({
      top: `
      ${topOffsetFromParent / 10}rem`,
      left: `${leftOffsetFromParent / 10}rem`,
    });
  };

  const hideRemover = () => setRemoverPosition(null);

  useLayoutEffect(() => {
    if (!isEditable) hideRemover();
  }, [isEditable]);

  return {
    removerPosition,
    updateRemoverPosition,
    hideRemover,
  };
};

export default useHighlightRemoverPosition;
