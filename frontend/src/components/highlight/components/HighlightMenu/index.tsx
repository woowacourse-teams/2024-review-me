import { useEffect, useRef } from 'react';

import { HIGHLIGHT_MENU_CLASS_NAME, HIGHLIGHT_MENU_WIDTH } from '@/constants';
import { Position } from '@/types';
import { findSelectionInfo, SelectionInfo } from '@/utils';

import HighlightButton from '../HighlightButton';
import { HighlightArea } from '../HighlightEditor/hooks/useCheckHighlight';

import * as S from './style';

interface HighlightMenuProps {
  position: Position;
  highlightArea: HighlightArea;
  addHighlightByDrag: (selectionInfo: SelectionInfo) => void;
  removeHighlightByDrag: (selectionInfo: SelectionInfo) => void;
}

const HighlightMenu = ({
  position,
  highlightArea,

  addHighlightByDrag,
  removeHighlightByDrag,
}: HighlightMenuProps) => {
  const menuStyleWidth = HIGHLIGHT_MENU_WIDTH[highlightArea];
  const selectionInfoRef = useRef<SelectionInfo | undefined>(undefined);

  const menuRef = useRef<HTMLDivElement>(null);

  const handleAddHighlight = () => {
    if (selectionInfoRef.current) {
      addHighlightByDrag(selectionInfoRef.current);
    }
  };

  const handleRemoveHighlight = () => {
    if (selectionInfoRef.current) {
      removeHighlightByDrag(selectionInfoRef.current);
    }
  };

  useEffect(() => {
    const newSelectionInfo = findSelectionInfo();
    selectionInfoRef.current = newSelectionInfo;
  }, [position, menuRef]);

  return (
    <S.Menu ref={menuRef} className={HIGHLIGHT_MENU_CLASS_NAME} $position={position} $width={menuStyleWidth}>
      {highlightArea !== 'full' && <HighlightButton.dragHighlightAdd addHighlightByDrag={handleAddHighlight} />}
      {highlightArea !== 'none' && (
        <HighlightButton.dragHighlightRemove removeHighlightByDrag={handleRemoveHighlight} />
      )}
    </S.Menu>
  );
};

export default HighlightMenu;
