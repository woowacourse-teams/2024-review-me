import { Button } from '@/components/common';
import { HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME } from '@/constants';
import { Position } from '@/types';

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
        <Button styleType="secondary" style={{ padding: '0.4rem 1rem' }} onClick={addHighlight}>
          Add
        </Button>
      ) : (
        <Button styleType="secondary" style={{ padding: '0.4rem 1rem' }} onClick={removeHighlight}>
          Remove
        </Button>
      )}
    </div>
  );
};

export default HighlightToggleButtonContainer;
