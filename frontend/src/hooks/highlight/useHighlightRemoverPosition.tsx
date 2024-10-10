import { useLayoutEffect, useState } from 'react';

import { Position } from '@/types';

interface UseHighlightRemoverPositionProps {
  isEditAble: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}
const useHighlightRemoverPosition = ({ isEditAble, editorRef }: UseHighlightRemoverPositionProps) => {
  const [removerPosition, setRemoverPosition] = useState<Position | null>(null);

  const updateRemoverPosition = (rect: DOMRect) => {
    const editorRect = editorRef.current?.getClientRects()[0];
    if (!editorRect) return;
    const top = rect.bottom - editorRect.top;
    const left = rect.right - editorRect.left;
    const GAP_WIDTH_SELECTION = 10;
    const buttonWidth = 31;

    const isOverEditorArea = editorRect.right < rect.right + buttonWidth;
    const topOffsetFromParent = isOverEditorArea ? top + GAP_WIDTH_SELECTION : top;
    const leftOffsetFromParent = isOverEditorArea ? editorRect.width - buttonWidth : left;

    setRemoverPosition({
      top: `
      ${topOffsetFromParent / 10}rem`,
      left: `${leftOffsetFromParent / 10}rem`,
    });
  };

  const hideRemover = () => setRemoverPosition(null);

  useLayoutEffect(() => {
    if (!isEditAble) hideRemover();
  }, [isEditAble]);

  return {
    removerPosition,
    updateRemoverPosition,
    hideRemover,
  };
};

export default useHighlightRemoverPosition;
