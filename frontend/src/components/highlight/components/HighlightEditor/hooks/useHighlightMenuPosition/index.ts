import { useState } from 'react';

import { Position } from '@/types';

import useDragHighlightPosition from '../useDragHighlightPosition';

interface UseHighlightMenuPositionProps {
  isEditable: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}

const useHighlightMenuPosition = ({ isEditable, editorRef }: UseHighlightMenuPositionProps) => {
  const [menuPosition, setMenuPosition] = useState<Position | null>(null);

  const updateHighlightMenuPosition = (position: Position | null) => setMenuPosition(position);

  const resetHighlightMenuPosition = () => {
    setMenuPosition(null);
  };

  const { updateHighlightMenuPositionByDrag } = useDragHighlightPosition({
    isEditable,
    editorRef,
    updateHighlightMenuPosition,
  });

  return {
    menuPosition,
    updateHighlightMenuPositionByDrag,
    resetHighlightMenuPosition,
  };
};

export default useHighlightMenuPosition;
