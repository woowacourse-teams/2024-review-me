import { useLayoutEffect, useState } from 'react';

interface Position {
  top: number;
  left: number;
}

interface UseHighlightRemoverPositionProps {
  isAbleEdit: boolean;
}
const useHighlightRemoverPosition = ({ isAbleEdit }: UseHighlightRemoverPositionProps) => {
  const [removerPosition, setRemoverPosition] = useState<Position | null>(null);

  const updateRemoverPosition = (rect: DOMRect) => {
    setRemoverPosition({
      top: rect.bottom,
      left: rect.right,
    });
  };

  const hideRemover = () => setRemoverPosition(null);

  useLayoutEffect(() => {
    if (!isAbleEdit) hideRemover();
  }, [isAbleEdit]);

  return {
    removerPosition,
    updateRemoverPosition,
    hideRemover,
  };
};

export default useHighlightRemoverPosition;
