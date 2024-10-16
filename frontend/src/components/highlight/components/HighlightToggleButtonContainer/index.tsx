import { Position } from '@/types';

import HighlightButton from '../HighlightButton';

interface HighlightToggleButtonContainerProps {
  buttonPosition: Position;
  isAddingHighlight: boolean;
  addHighlight: () => void;
  removeHighlightByDrag: () => void;
}
/**
 *선택된 영역의 하이라이트 적용 여부에 따라 추가 또는 삭제 버튼을 보여주는 컴포넌트
 */
const HighlightToggleButtonContainer = ({
  buttonPosition,
  isAddingHighlight,
  addHighlight,
  removeHighlightByDrag,
}: HighlightToggleButtonContainerProps) => {
  return (
    <>
      {isAddingHighlight ? (
        <HighlightButton.highlighter addHighlight={addHighlight} position={buttonPosition} />
      ) : (
        <HighlightButton.dragRemoval removeHighlightByDrag={removeHighlightByDrag} position={buttonPosition} />
      )}
    </>
  );
};

export default HighlightToggleButtonContainer;
