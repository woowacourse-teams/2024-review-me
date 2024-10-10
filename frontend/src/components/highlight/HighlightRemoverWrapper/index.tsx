import { Position } from '@/types';

import HighlightButton from '../HighlightButton';

interface HighlightRemoverWrapperProps {
  buttonPosition: Position;
  removeHighlightByClick: () => void;
}
/**
 * 하이라이트 된 span 태그 클릭 시,  해당 하이라이트를 삭제할 수 있는 버튼을 띄우는 컴포넌트
 */
const HighlightRemoverWrapper = ({ buttonPosition, removeHighlightByClick }: HighlightRemoverWrapperProps) => {
  return <HighlightButton.clickRemoval removeHighlightByClick={removeHighlightByClick} position={buttonPosition} />;
};

export default HighlightRemoverWrapper;
