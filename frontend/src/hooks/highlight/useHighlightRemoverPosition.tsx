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

    setRemoverPosition({
      top: `
      ${(rect.bottom - editorRect.top) / 10}rem`,
      left: `${(rect.right - editorRect.left) / 10}rem`,
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
