import EraserIcon from '@/assets/eraser.svg';
import HighlighterIcon from '@/assets/highlighter.svg';
import TrashIcon from '@/assets/trash.svg';
import {
  HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME,
  HIGHLIGHT_BUTTON_SIZE,
  HIGHLIGHT_REMOVER_CLASS_NAME,
  SR_ONLY,
} from '@/constants';
import { Position } from '@/types';

import * as S from './style';

interface DragHighlightAddButtonProps {
  position: Position;
  addHighlightByDrag: () => void;
}

const DragHighlightAddButton = ({ addHighlightByDrag, position }: DragHighlightAddButtonProps) => {
  return (
    <S.Button
      className={HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME}
      onClick={addHighlightByDrag}
      $position={position}
      $width={HIGHLIGHT_BUTTON_SIZE.width.buttonWidthColor}
    >
      <span className={SR_ONLY}>하이라이트 추가 버튼</span>
      <S.ButtonIcon src={HighlighterIcon} alt="" />
      <S.Color aria-label="하이라이트 색상" />
    </S.Button>
  );
};

interface DragHighlightRemoveButtonProps {
  removeHighlightByDrag: () => void;
  position: Position;
}

const DragHighlightRemoveButton = ({ removeHighlightByDrag, position }: DragHighlightRemoveButtonProps) => {
  return (
    <S.Button
      className={HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME}
      onClick={removeHighlightByDrag}
      $position={position}
      $width={HIGHLIGHT_BUTTON_SIZE.width.basic}
    >
      <span className={SR_ONLY}>하이라이트 삭제 버튼</span>
      <S.ButtonIcon src={EraserIcon} alt="" />
    </S.Button>
  );
};

interface HighlightClickRemovalProps {
  removeHighlightByClick: () => void;
  position: Position;
}

const HighlightClickRemoval = ({ removeHighlightByClick, position }: HighlightClickRemovalProps) => {
  return (
    <S.Button
      className={HIGHLIGHT_REMOVER_CLASS_NAME}
      onClick={removeHighlightByClick}
      $position={position}
      $width={HIGHLIGHT_BUTTON_SIZE.width.basic}
    >
      <span className={SR_ONLY}>하이라이트 삭제 버튼</span>
      <S.ButtonIcon src={TrashIcon} alt="" />
    </S.Button>
  );
};

const HighlightButton = {
  dragHighlightAdd: DragHighlightAddButton,
  dragHighlightRemove: DragHighlightRemoveButton,
};

export default HighlightButton;
