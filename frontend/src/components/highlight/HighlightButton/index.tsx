import EraserIcon from '@/assets/eraser.svg';
import HighlighterIcon from '@/assets/highlighter.svg';
import TrashIcon from '@/assets/trash.svg';
import { HIGHLIGHT_REMOVER_CLASS_NAME, SR_ONLY } from '@/constants';

import * as S from './style';

interface HighlighterButtonProps {
  addHighlight: () => void;
}

const HighlighterButton = ({ addHighlight }: HighlighterButtonProps) => {
  return (
    <S.Button onClick={addHighlight}>
      <span className={SR_ONLY}>하이라이트 추가 버튼</span>
      <S.ButtonIcon src={HighlighterIcon} alt="" />
      <S.Color aria-label="하이라이트 색상" />
    </S.Button>
  );
};

interface HighlightDragRemovalProps {
  removeHighlightByDrag: () => void;
}

const HighlightDragRemoval = ({ removeHighlightByDrag }: HighlightDragRemovalProps) => {
  return (
    <S.Button onClick={removeHighlightByDrag} name="">
      <span className={SR_ONLY}>하이라이트 삭제 버튼</span>
      <S.ButtonIcon src={EraserIcon} alt="" />
    </S.Button>
  );
};

interface HighlightClickRemovalProps {
  removeHighlightByClick: () => void;
}

const HighlightClickRemoval = ({ removeHighlightByClick }: HighlightClickRemovalProps) => {
  return (
    <S.Button className={HIGHLIGHT_REMOVER_CLASS_NAME} onClick={removeHighlightByClick}>
      <span className={SR_ONLY}>하이라이트 삭제 버튼</span>
      <S.ButtonIcon src={TrashIcon} alt="" />
    </S.Button>
  );
};

const HighlightButton = {
  highlighter: HighlighterButton,
  dragRemoval: HighlightDragRemoval,
  clickRemoval: HighlightClickRemoval,
};

export default HighlightButton;
