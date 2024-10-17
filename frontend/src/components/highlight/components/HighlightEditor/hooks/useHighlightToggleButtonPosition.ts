import { useLayoutEffect, useState } from 'react';

import { GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON, HIGHLIGHT_BUTTON_SIZE } from '@/constants';
import { Position } from '@/types';
import { EditorSelectionInfo } from '@/utils';

interface UseHighlightButtonPositionProps {
  isEditable: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}

const useHighlightToggleButtonPosition = ({ isEditable, editorRef }: UseHighlightButtonPositionProps) => {
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
    const buttonHight = HIGHLIGHT_BUTTON_SIZE.height;
    const { basic: buttonBasicWidth, buttonWidthColor: addButtonWidth } = HIGHLIGHT_BUTTON_SIZE.width;
    const buttonWidth = isAddingHighlight ? addButtonWidth : buttonBasicWidth;

    const rectLeft = isForwardDrag ? lastRect.right : lastRect.left;
    const left = rectLeft - editorRect.left;
    const top =
      lastRect.top -
      (isForwardDrag ? 0 : startBlock.clientHeight + buttonHight + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON) -
      editorRect.top +
      buttonHight;

    const isOverEditorArea = editorRect.right < rectLeft + buttonWidth;
    const leftOffsetFromParent = isOverEditorArea ? editorRect.width - buttonWidth : left;
    const topOffsetFromParent = top;
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
    if (!isEditable) hideHighlightToggleButton();
  }, [isEditable]);

  useLayoutEffect(() => {});

  return {
    highlightToggleButtonPosition,
    hideHighlightToggleButton,
    updateHighlightToggleButtonPosition,
  };
};

export default useHighlightToggleButtonPosition;
