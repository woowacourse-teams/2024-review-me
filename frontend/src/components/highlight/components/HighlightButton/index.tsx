import EraserIcon from '@/assets/eraser.svg';
import HighlighterIcon from '@/assets/highlighter.svg';
import { ImgWithSkeleton } from '@/components/skeleton';

import * as S from './style';

const BUTTON_ICON_STYLE = {
  width: '1.6rem',
  height: '1.6rem',
};

interface DragHighlightAddButtonProps {
  addHighlightByDrag: () => void;
}

const DragHighlightAddButton = ({ addHighlightByDrag }: DragHighlightAddButtonProps) => {
  return (
    <S.Button onClick={addHighlightByDrag} aria-label="하이하이트 추가 버튼">
      <ImgWithSkeleton imgWidth={BUTTON_ICON_STYLE.width} imgHeight={BUTTON_ICON_STYLE.height}>
        <S.ButtonIcon
          src={HighlighterIcon}
          alt=""
          $height={BUTTON_ICON_STYLE.height}
          $width={BUTTON_ICON_STYLE.width}
        />
      </ImgWithSkeleton>
    </S.Button>
  );
};

interface DragHighlightRemoveButtonProps {
  removeHighlightByDrag: () => void;
}

const DragHighlightRemoveButton = ({ removeHighlightByDrag }: DragHighlightRemoveButtonProps) => {
  return (
    <S.Button onClick={removeHighlightByDrag} aria-label="하이라이트 삭제 버튼">
      <ImgWithSkeleton imgWidth={BUTTON_ICON_STYLE.width} imgHeight={BUTTON_ICON_STYLE.height}>
        <S.ButtonIcon src={EraserIcon} alt="" $height={BUTTON_ICON_STYLE.height} $width={BUTTON_ICON_STYLE.width} />
      </ImgWithSkeleton>
    </S.Button>
  );
};

const HighlightButton = {
  dragHighlightAdd: DragHighlightAddButton,
  dragHighlightRemove: DragHighlightRemoveButton,
};

export default HighlightButton;
