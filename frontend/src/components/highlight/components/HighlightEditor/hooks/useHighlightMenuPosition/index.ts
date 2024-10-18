import { useState } from 'react';

import { Position } from '@/types';

import useDragHighlightPosition from '../useDragHighlightPosition';
import useLongPressHighlightPosition from '../useLongPressHighlightPosition';

interface UseHighlightMenuPositionProps {
  isEditable: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}

const useHighlightMenuPosition = ({ isEditable, editorRef }: UseHighlightMenuPositionProps) => {
  const [menuPosition, setMenuPosition] = useState<Position | null>(null);

  const updateHighlightMenuPosition = (position: Position | null) => setMenuPosition(position);

  const hideHighlightMenu = () => {
    setMenuPosition(null);
  };

  const { updateHighlightMenuPositionByDrag } = useDragHighlightPosition({
    isEditable,
    editorRef,
    updateHighlightMenuPosition,
  });

  const { updateHighlightMenuPositionByLongPress } = useLongPressHighlightPosition({
    isEditable,
    editorRef,
    updateHighlightMenuPosition,
  });

  return {
    menuPosition,
    updateHighlightMenuPositionByDrag,
    updateHighlightMenuPositionByLongPress,
    hideHighlightMenu,
  };
};

export default useHighlightMenuPosition;
