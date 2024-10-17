import { Position } from '@/types';

import HighlightButton from '../HighlightButton';

interface LongPressHighlightButtonWrapperProps {
  buttonPosition: Position;
  removeHighlightByLongPress: () => void;
}
/**
 * 하이라이트 된 span 태그 클릭 시,  해당 하이라이트를 삭제할 수 있는 버튼을 띄우는 컴포넌트
 */
const LongPressHighlightButtonWrapper = ({
  buttonPosition,
  removeHighlightByLongPress,
}: LongPressHighlightButtonWrapperProps) => {
  return (
    <HighlightButton.longPressHighlightRemove
      removeHighlightByLongPress={removeHighlightByLongPress}
      position={buttonPosition}
    />
  );
};

export default LongPressHighlightButtonWrapper;
