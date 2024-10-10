import { useLayoutEffect, useState } from 'react';

import { Position } from '@/types';
import { EditorSelectionInfo } from '@/utils';

interface UseHighlightButtonPositionProps {
  isEditAble: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}

const useHighlightToggleButtonPosition = ({ isEditAble, editorRef }: UseHighlightButtonPositionProps) => {
  const [highlightToggleButtonPosition, setHighlightToggleButtonPosition] = useState<Position | null>(null);

  const hideHighlightToggleButton = () => setHighlightToggleButtonPosition(null);

  interface CalculateEndPositionParams {
    info: EditorSelectionInfo;
    isAddingHighlight: boolean;
  }
  const calculateEndPosition = ({ info, isAddingHighlight }: CalculateEndPositionParams) => {
    const { selection, isForwardDrag, startBlock } = info;
    if (!editorRef.current) return;
    const range = selection.getRangeAt(0);
    const rects = range.getClientRects();
    const editorRect = editorRef.current.getClientRects()[0];

    if (rects.length === 0) return;

    // 드래그 방향에 따른 마지막 rect의 좌표 정보를 가져옴 (마우스가 놓인 최종 지점)
    const lastRect = rects[isForwardDrag ? rects.length - 1 : 0];

    const GAP_WIDTH_SELECTION = 10;
    const rectLeft = isForwardDrag ? lastRect.right + GAP_WIDTH_SELECTION : lastRect.left - GAP_WIDTH_SELECTION;

    const left = rectLeft - editorRect.left;
    const top = lastRect.top - (isForwardDrag ? 0 : startBlock.clientHeight) - editorRect.top;
    // TODO : 리팩토링 시 버튼 width, height 상수화해서 사용하기
    const buttonWidth = isAddingHighlight ? 54 : 31;
    const buttonHight = 25;

    const isOverEditorArea = editorRect.right < rectLeft + buttonWidth;
    const leftOffsetFromParent = isOverEditorArea ? editorRect.width - buttonWidth : left;
    const topOffsetFromParent = isOverEditorArea ? top + buttonHight + GAP_WIDTH_SELECTION : top;

    const endPosition: Position = {
      left: `${leftOffsetFromParent / 10}rem`,
      top: `${topOffsetFromParent / 10}rem`,
    };

    return endPosition;
  };

  const updateHighlightToggleButtonPosition = ({ info, isAddingHighlight }: CalculateEndPositionParams) => {
    const endPosition = calculateEndPosition({ info, isAddingHighlight });
    if (!endPosition) return console.error('endPosition을 찾을 수 없어요.');

    setHighlightToggleButtonPosition(endPosition);
  };

  useLayoutEffect(() => {
    if (!isEditAble) hideHighlightToggleButton();
  }, [isEditAble]);

  useLayoutEffect(() => {});

  return {
    highlightToggleButtonPosition,
    hideHighlightToggleButton,
    updateHighlightToggleButtonPosition,
  };
};

export default useHighlightToggleButtonPosition;
