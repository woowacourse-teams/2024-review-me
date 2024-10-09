import { useLayoutEffect, useState } from 'react';

interface Position {
  top: number;
  left: number;
}

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
      top: rect.bottom - editorRect.top,
      left: rect.right - editorRect.left,
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
