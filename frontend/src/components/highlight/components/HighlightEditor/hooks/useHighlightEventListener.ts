import { useEffect } from 'react';

import { HIGHLIGHT_MENU_CLASS_NAME } from '@/constants';
import { findSelectionInfo, isTouchDevice, SelectionInfo } from '@/utils';

import { HighlightArea } from './useCheckHighlight';
import { UseDragHighlightPositionReturn } from './useDragHighlightPosition';

interface UseHighlightEventListenerProps extends UseDragHighlightPositionReturn {
  isEditable: boolean;
  hideHighlightMenu: () => void;
  checkHighlight: (info: SelectionInfo) => HighlightArea;
}

/**
 * document에 형광펜 관련 이벤트를 붙이는 훅
 */
const useHighlightEventListener = ({
  isEditable,
  updateHighlightMenuPositionByDrag,
  hideHighlightMenu,
  checkHighlight,
}: UseHighlightEventListenerProps) => {
  const hideHighlightButton = (e: MouseEvent | TouchEvent) => {
    if (!isEditable) return;

    const isInHighlightMenu = (e.target as HTMLElement).closest(`.${HIGHLIGHT_MENU_CLASS_NAME}`);
    if (!isInHighlightMenu) hideHighlightMenu();
  };

  const showHighlightButton = () => {
    if (!isEditable) return;
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    const highlightArea = checkHighlight(selectionInfo);
    updateHighlightMenuPositionByDrag({ selectionInfo, highlightArea });
  };

  /**
   * document에 형광펜 이벤트 적용
   */
  const addHighlightEvent = () => {
    document.addEventListener('mousedown', hideHighlightButton);
    document.addEventListener('mouseup', showHighlightButton);
    // NOTE: 터치가 가능한 기기에서는 touchstart, touchend 보다 selectionchange를 사용하는 게 오류가 없음
    if (isTouchDevice()) {
      document.addEventListener('selectionchange', showHighlightButton);
      document.addEventListener('contextmenu', hideContextMenuInTouch);
    }
  };
  /**
   * 터치 브라우저에서, 글자 길게 선택 시 나오는 브라우저 기본 컨텍스트 메뉴 보이지 않게 처리하는 핸들러
   * @param event
   */
  const hideContextMenuInTouch = (event: MouseEvent) => {
    event.preventDefault();
  };
  /**
   * document에 형광펜 이벤트 삭제
   */
  const removeHighlightEvent = () => {
    document.removeEventListener('mouseup', showHighlightButton);
    document.removeEventListener('mousedown', hideHighlightButton);
    if (isTouchDevice()) {
      document.removeEventListener('contextmenu', hideContextMenuInTouch);
      document.removeEventListener('selectionChange', showHighlightButton);
    }
  };

  useEffect(() => {
    isEditable ? addHighlightEvent() : removeHighlightEvent();

    return () => {
      removeHighlightEvent();
    };
  }, [isEditable]);
};

export default useHighlightEventListener;
