import { HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME } from '@/constants';
import { Position } from '@/types';

import HighlightButton from '../HighlightButton';

interface HighlightToggleButtonContainerProps {
  buttonPosition: Position;
  isAddingHighlight: boolean;
  addHighlight: () => void;
  removeHighlight: () => void;
}
/**
 *선택된 영역의 하이라이트 적용 여부에 따라 추가 또는 삭제 버튼을 보여주는 컴포넌트
 */
const HighlightToggleButtonContainer = ({
  buttonPosition,
  isAddingHighlight,
  addHighlight,
  removeHighlight,
}: HighlightToggleButtonContainerProps) => {
  return (
    <div className={HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME} style={{ position: 'fixed', ...buttonPosition }}>
      {isAddingHighlight ? (
        <HighlightButton.highlighter addHighlight={addHighlight} />
      ) : (
        <HighlightButton.dragRemoval removeHighlightByDrag={removeHighlight} />
      )}
    </div>
  );
};

export default HighlightToggleButtonContainer;
