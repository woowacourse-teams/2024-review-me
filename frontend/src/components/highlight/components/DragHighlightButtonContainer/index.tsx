import { Position } from '@/types';

import HighlightButton from '../HighlightButton';

interface DragHighlightButtonContainerProps {
  buttonPosition: Position;
  isAddingHighlight: boolean;
  addHighlightByDrag: () => void;
  removeHighlightByDrag: () => void;
}
/**
 *선택된 영역의 하이라이트 적용 여부에 따라 추가 또는 삭제 버튼을 보여주는 컴포넌트
 */
const DragHighlightButtonContainer = ({
  buttonPosition,
  isAddingHighlight,
  addHighlightByDrag,
  removeHighlightByDrag,
}: DragHighlightButtonContainerProps) => {
  return (
    <>
      {isAddingHighlight ? (
        <HighlightButton.dragHighlightAdd addHighlightByDrag={addHighlightByDrag} position={buttonPosition} />
      ) : (
        <HighlightButton.dragHighlightRemove removeHighlightByDrag={removeHighlightByDrag} position={buttonPosition} />
      )}
    </>
  );
};

export default DragHighlightButtonContainer;
