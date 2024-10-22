import EraserIcon from '@/assets/eraser.svg';
import HighlighterIcon from '@/assets/highlighter.svg';
import TrashIcon from '@/assets/trash.svg';

import * as S from './style';

interface DragHighlightAddButtonProps {
  addHighlightByDrag: () => void;
}

const DragHighlightAddButton = ({ addHighlightByDrag }: DragHighlightAddButtonProps) => {
  return (
    <S.Button onClick={addHighlightByDrag} aria-label="하이하이트 추가 버튼">
      <S.ButtonIcon src={HighlighterIcon} alt="" />
    </S.Button>
  );
};

interface DragHighlightRemoveButtonProps {
  removeHighlightByDrag: () => void;
}

const DragHighlightRemoveButton = ({ removeHighlightByDrag }: DragHighlightRemoveButtonProps) => {
  return (
    <S.Button onClick={removeHighlightByDrag} aria-label="하이라이트 삭제 버튼">
      <S.ButtonIcon src={EraserIcon} alt="" />
    </S.Button>
  );
};

interface LongPressHighlightRemoveButtonProps {
  removeHighlightByLongPress: () => void;
}

const LongPressHighlightRemoveButton = ({ removeHighlightByLongPress }: LongPressHighlightRemoveButtonProps) => {
  return (
    <S.Button onClick={removeHighlightByLongPress} aria-label="하이라이트 삭제 버튼">
      <S.ButtonIcon src={TrashIcon} alt="" />
    </S.Button>
  );
};

const HighlightButton = {
  dragHighlightAdd: DragHighlightAddButton,
  dragHighlightRemove: DragHighlightRemoveButton,
  longPressHighlightRemove: LongPressHighlightRemoveButton,
};

export default HighlightButton;
