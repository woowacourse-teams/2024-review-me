import { Button } from '@/components/common';
import { HIGHLIGHT_REMOVER_CLASS_NAME } from '@/constants';
import { Position } from '@/types';

interface HighlightRemoverWrapperProps {
  buttonPosition: Position;
  handleClickRemover: () => void;
}
/**
 * 하이라이트 된 span 태그 클릭 시,  해당 하이라이트를 삭제할 수 있는 버튼을 띄우는 컴포넌트
 */
const HighlightRemoverWrapper = ({ buttonPosition, handleClickRemover }: HighlightRemoverWrapperProps) => {
  return (
    <div style={{ position: 'fixed', ...buttonPosition }}>
      <Button
        styleType="secondary"
        className={HIGHLIGHT_REMOVER_CLASS_NAME}
        style={{ padding: '0.4rem 1rem' }}
        onClick={handleClickRemover}
      >
        x
      </Button>
    </div>
  );
};

export default HighlightRemoverWrapper;
