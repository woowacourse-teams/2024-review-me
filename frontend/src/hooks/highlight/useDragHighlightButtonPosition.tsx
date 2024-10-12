import { useLayoutEffect, useState } from 'react';

import { GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON, HIGHLIGHT_BUTTON_SIZE } from '@/constants';
import { Position } from '@/types';
import { SelectionInfo } from '@/utils';

interface UseDragButtonPositionProps {
  isEditable: boolean;
  editorRef: React.RefObject<HTMLDivElement>;
}

const useDragHighlightButtonPosition = ({ isEditable, editorRef }: UseDragButtonPositionProps) => {
  const [dragHighlightButtonPosition, setDragHighlightButtonPosition] = useState<Position | null>(null);

  const hideDragHighlightButton = () => setDragHighlightButtonPosition(null);

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
   * @param buttonHeight  화면에 보이는 하이라이터 토글 버튼 높이
   */
  const calculateRectOffsets = (
    lastRect: DOMRect,
    editorRect: DOMRect,
    isForwardDrag: boolean,
    buttonHeight: number,
  ) => {
    //뷰포트 기준 위치
    const rectLeft = isForwardDrag ? lastRect.right : lastRect.left;
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
   * @param buttonHeight  토글 버튼의 height
   * @param editorRect  editor DOMRect
   */
  const checkOverflow = (
    rectLeft: number,
    rectTop: number,
    buttonWidth: number,
    buttonHeight: number,
    editorRect: DOMRect,
  ) => {
    const isOverflowingHorizontally = editorRect.right < rectLeft + buttonWidth;
    const isOverflowingVertically = editorRect.bottom < rectTop + buttonHeight;

    return { isOverflowingHorizontally, isOverflowingVertically };
  };

  interface CalculateDragHighlightButtonPosition {
    leftOffsetFromEditor: number;
    topOffsetFromEditor: number;
    buttonWidth: number;
    buttonHeight: number;
    isOverflowingHorizontally: boolean;
    isOverflowingVertically: boolean;
    editorRect: DOMRect;
    lastRect: DOMRect;
  }
  const calculateDragHighlightButtonPosition = ({
    leftOffsetFromEditor,
    topOffsetFromEditor,
    buttonWidth,
    buttonHeight,
    isOverflowingHorizontally,
    isOverflowingVertically,
    editorRect,
    lastRect,
  }: CalculateDragHighlightButtonPosition) => {
    const left = isOverflowingHorizontally ? editorRect.width - buttonWidth : leftOffsetFromEditor;
    const top = isOverflowingVertically
      ? topOffsetFromEditor - lastRect.height - GAP_WIDTH_SELECTION_AND_HIGHLIGHT_BUTTON * 2 - buttonHeight
      : topOffsetFromEditor;

    return { left, top };
  };

  const getButtonSize = (isAddingHighlight: boolean) => {
    const buttonHeight = HIGHLIGHT_BUTTON_SIZE.height;
    const { basic: buttonBasicWidth, buttonWidthColor: addButtonWidth } = HIGHLIGHT_BUTTON_SIZE.width;
    const buttonWidth = isAddingHighlight ? addButtonWidth : buttonBasicWidth;

    return {
      buttonHeight,
      buttonWidth,
    };
  };
  interface getDragHighlightButtonParams {
    selectionInfo: SelectionInfo;
    isAddingHighlight: boolean;
  }

  const getDragHighlightButtonPosition = ({ selectionInfo, isAddingHighlight }: getDragHighlightButtonParams) => {
    const { isForwardDrag } = selectionInfo;

    const rects = getRects({ selectionInfo, editorRef });
    if (!rects) return;

    const { lastRect, editorRect } = rects;
    const { buttonHeight, buttonWidth } = getButtonSize(isAddingHighlight);

    const { leftOffsetFromEditor, topOffsetFromEditor, rectLeft, rectTop } = calculateRectOffsets(
      lastRect,
      editorRect,
      isForwardDrag,
      buttonHeight,
    );
    const { isOverflowingHorizontally, isOverflowingVertically } = checkOverflow(
      rectLeft,
      rectTop,
      buttonWidth,
      buttonHeight,
      editorRect,
    );
    const { left, top } = calculateDragHighlightButtonPosition({
      leftOffsetFromEditor,
      topOffsetFromEditor,
      buttonWidth,
      buttonHeight,
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

  const updateDragHighlightButtonPosition = ({ selectionInfo, isAddingHighlight }: getDragHighlightButtonParams) => {
    const position = getDragHighlightButtonPosition({ selectionInfo, isAddingHighlight });
    if (!position) return console.error('endPosition을 찾을 수 없어요.');

    setDragHighlightButtonPosition(position);
  };

  useLayoutEffect(() => {
    if (!isEditable) hideDragHighlightButton();
  }, [isEditable]);

  useLayoutEffect(() => {});

  return {
    dragHighlightButtonPosition,
    hideDragHighlightButton,
    updateDragHighlightButtonPosition,
  };
};

export default useDragHighlightButtonPosition;
