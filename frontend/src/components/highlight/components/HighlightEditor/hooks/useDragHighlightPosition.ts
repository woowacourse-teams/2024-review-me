import { useLayoutEffect } from 'react';

import { GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON, HIGHLIGHT_MENU_STYLE_SIZE, HIGHLIGHT_MENU_WIDTH } from '@/constants';
import { Position } from '@/types';
import { isTouchDevice, SelectionInfo } from '@/utils';

import { HighlightArea } from './useCheckHighlight';

interface UseDragHighlightPositionProps {
  isEditable: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
  updateHighlightMenuPosition: (position: Position | null) => void;
}

export interface getDragHighlightParams {
  selectionInfo: SelectionInfo;
  highlightArea: HighlightArea;
}

const useDragHighlightPosition = ({
  isEditable,
  editorRef,
  updateHighlightMenuPosition,
}: UseDragHighlightPositionProps) => {
  //위치 계산
  interface GetRectsParams {
    selectionInfo: SelectionInfo;
    editorRef: React.RefObject<HTMLDivElement>;
  }
  /**
   * 드래그 시 마지막으로 선택된 Node와 editor의 DOMRect를 반환하는 함수
   */
  const getRects = ({ selectionInfo, editorRef }: GetRectsParams) => {
    if (!editorRef.current) return console.error('editorRef 값이 없어요.');

    const { selection, isForwardDrag } = selectionInfo;
    const range = selection.getRangeAt(0);
    const rects = range.getClientRects();
    const editorRect = editorRef.current.getClientRects()[0];

    if (rects.length === 0) return console.error('선택된 글자가 없어요.');

    const lastRect = rects[isForwardDrag ? rects.length - 1 : 0];

    return {
      editorRect,
      lastRect,
    };
  };

  /**
   *
   * @param lastRect  드래그 시 마지막으로 선택된 Node의 DOMRect
   * @param editorRect editor DOMRect
   * @param isForwardDrag 드래그가 정방향인지 여부
   */
  const calculateRectOffsets = (
    lastRect: DOMRect,
    editorRect: DOMRect,
    isForwardDrag: boolean,
    buttonWidth: number,
  ) => {
    const { height: buttonHeight } = HIGHLIGHT_MENU_STYLE_SIZE;
    const isTouch = isTouchDevice();
    //뷰포트 기준 위치
    const rectLeft = isForwardDrag ? lastRect.right - (isTouch ? buttonWidth : 0) : lastRect.left;
    const rectTop = isForwardDrag
      ? lastRect.bottom + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON
      : lastRect.top - buttonHeight - GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON;

    // 에디터 기준 위치
    const leftOffsetFromEditor = rectLeft - editorRect.left;
    const topOffsetFromEditor = rectTop - editorRect.top;

    return { leftOffsetFromEditor, topOffsetFromEditor, rectLeft, rectTop };
  };

  /**
   *  토글 버튼이 editor를 영역을 벗어나는지 여부를 계산하는 함수
   * @param rectLeft 토글 버튼의 뷰기준 left 위치
   * @param rectTop  토클 버튼의 뷰기준 top 위치
   * @param buttonWidth  토글 버튼의 width
   * @param editorRect  editor DOMRect
   */
  const checkOverflow = (rectLeft: number, rectTop: number, buttonWidth: number, editorRect: DOMRect) => {
    const { shadow: shadowWidth, height: buttonHeight } = HIGHLIGHT_MENU_STYLE_SIZE;
    const buttonTotalHeight = buttonHeight + shadowWidth;
    const buttonTotalWidth = buttonWidth + shadowWidth;

    const isOverflowingHorizontally = {
      right: editorRect.right < rectLeft + buttonTotalWidth,
      left: rectLeft - buttonTotalWidth < editorRect.left,
    };
    const isOverflowingVertically = {
      top: rectTop - buttonTotalHeight - GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON <= editorRect.top,
      bottom: editorRect.bottom <= rectTop + buttonTotalHeight + GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON,
    };

    return { isOverflowingHorizontally, isOverflowingVertically };
  };

  interface CalculateDragHighlightMenuPosition {
    leftOffsetFromEditor: number;
    topOffsetFromEditor: number;
    buttonWidth: number;
    isOverflowingHorizontally: { left: boolean; right: boolean };
    isOverflowingVertically: { top: boolean; bottom: boolean };
    editorRect: DOMRect;
    lastRect: DOMRect;
  }

  const calculateDragHighlightMenuPosition = ({
    leftOffsetFromEditor,
    topOffsetFromEditor,
    buttonWidth,
    isOverflowingHorizontally,
    isOverflowingVertically,
    editorRect,
    lastRect,
  }: CalculateDragHighlightMenuPosition) => {
    const { height: buttonHeight, shadow: shadowWidth } = HIGHLIGHT_MENU_STYLE_SIZE;
    const buttonTotalHeight = buttonHeight + shadowWidth;
    const buttonTotalWidth = buttonWidth + shadowWidth;

    let left = leftOffsetFromEditor;
    let top = topOffsetFromEditor;

    // left 계산
    if (isOverflowingHorizontally.right) {
      left = editorRect.width - buttonTotalWidth;
    }
    if (isOverflowingHorizontally.left) {
      left = shadowWidth;
    }

    // top 계산
    if (isOverflowingVertically.bottom) {
      top = topOffsetFromEditor - lastRect.height - GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON - buttonTotalHeight;
    }
    if (isOverflowingVertically.top) {
      top = shadowWidth;
    }

    return { left, top };
  };

  const getDragHighlightPosition = ({ selectionInfo, highlightArea }: getDragHighlightParams) => {
    const { isForwardDrag } = selectionInfo;

    const rects = getRects({ selectionInfo, editorRef });
    if (!rects) return;

    const { lastRect, editorRect } = rects;
    const buttonWidth = HIGHLIGHT_MENU_WIDTH[highlightArea];

    const { leftOffsetFromEditor, topOffsetFromEditor, rectLeft, rectTop } = calculateRectOffsets(
      lastRect,
      editorRect,
      isForwardDrag,
      buttonWidth,
    );
    const { isOverflowingHorizontally, isOverflowingVertically } = checkOverflow(
      rectLeft,
      rectTop,
      buttonWidth,

      editorRect,
    );
    const { left, top } = calculateDragHighlightMenuPosition({
      leftOffsetFromEditor,
      topOffsetFromEditor,
      buttonWidth,
      isOverflowingHorizontally,
      isOverflowingVertically,
      editorRect,
      lastRect,
    });

    const position: Position = {
      left: `${left / 10}rem`,
      top: `${top / 10}rem`,
    };

    return position;
  };

  const updateHighlightMenuPositionByDrag = ({ selectionInfo, highlightArea }: getDragHighlightParams) => {
    const position = getDragHighlightPosition({ selectionInfo, highlightArea });
    if (!position) return console.error('endPosition을 찾을 수 없어요.');

    updateHighlightMenuPosition(position);
  };

  useLayoutEffect(() => {
    if (!isEditable) updateHighlightMenuPosition(null);
  }, [isEditable]);

  return {
    updateHighlightMenuPositionByDrag,
  };
};

export default useDragHighlightPosition;

export type UseDragHighlightPositionReturn = ReturnType<typeof useDragHighlightPosition>;
