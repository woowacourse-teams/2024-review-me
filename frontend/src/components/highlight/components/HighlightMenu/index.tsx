import { HIGHLIGHT_MENU_CLASS_NAME, HIGHLIGHT_MENU_WIDTH } from '@/constants';
import { Position } from '@/types';

import HighlightButton from '../HighlightButton';
import { HighlightArea } from '../HighlightEditor/hooks/useCheckHighlight';

import * as S from './style';

interface HighlightMenuProps {
  position: Position;
  highlightArea: HighlightArea;
  isOpenLongPressRemove: boolean;
  addHighlightByDrag: () => void;
  removeHighlightByDrag: () => void;
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

  return (
    <S.Menu className={HIGHLIGHT_MENU_CLASS_NAME} $position={position} $width={menuStyleWidth}>
      {isOpenLongPressRemove && (
        <HighlightButton.longPressHighlightRemove removeHighlightByLongPress={removeHighlightByLongPress} />
      )}
      {!isOpenLongPressRemove && (
        <>
          {highlightArea !== 'full' && <HighlightButton.dragHighlightAdd addHighlightByDrag={addHighlightByDrag} />}
          {highlightArea !== 'none' && (
            <HighlightButton.dragHighlightRemove removeHighlightByDrag={removeHighlightByDrag} />
          )}
        </>
      )}
    </S.Menu>
  );
};

export default HighlightMenu;
