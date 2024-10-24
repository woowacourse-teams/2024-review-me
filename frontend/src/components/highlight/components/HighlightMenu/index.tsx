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
  isOpenLongPressRemove: boolean;
  addHighlightByDrag: (selectionInfo: SelectionInfo) => void;
  removeHighlightByDrag: (selectionInfo: SelectionInfo) => void;
  removeHighlightByLongPress: () => void;
}

const HighlightMenu = ({
  position,
  highlightArea,
  isOpenLongPressRemove,
  addHighlightByDrag,
  removeHighlightByDrag,
  removeHighlightByLongPress,
}: HighlightMenuProps) => {
  const menuStyleWidth = HIGHLIGHT_MENU_WIDTH[isOpenLongPressRemove ? 'longPress' : highlightArea];
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
      {isOpenLongPressRemove && (
        <HighlightButton.longPressHighlightRemove removeHighlightByLongPress={removeHighlightByLongPress} />
      )}
      {!isOpenLongPressRemove && (
        <>
          {highlightArea !== 'full' && <HighlightButton.dragHighlightAdd addHighlightByDrag={handleAddHighlight} />}
          {highlightArea !== 'none' && (
            <HighlightButton.dragHighlightRemove removeHighlightByDrag={handleRemoveHighlight} />
          )}
        </>
      )}
    </S.Menu>
  );
};

export default HighlightMenu;
