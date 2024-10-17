import { useEffect, useRef } from 'react';

import GrayHighlighterIcon from '@/assets/grayHighlighter.svg';
import PrimaryHighlighterIcon from '@/assets/primaryHighlighter.svg';
import {
  EDITOR_ANSWER_CLASS_NAME,
  EDITOR_LINE_CLASS_NAME,
  HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME,
  HIGHLIGHT_REMOVER_CLASS_NAME,
} from '@/constants';
import { ReviewAnswerResponseData } from '@/types';
import { findSelectionInfo, isTouchDevice } from '@/utils';

import DragHighlightButtonContainer from '../DragHighlightButtonContainer';
import EditorLineBlock from '../EditorLineBlock';
import EditSwitchButton from '../EditSwitchButton';
import LongPressHighlightButtonWrapper from '../LongPressHighlightButtonWrapper';

import {
  useDragHighlightButtonPosition,
  useHighlight,
  useCheckHighlight,
  useLongPressHighlightButtonPosition,
  useLongPress,
  useEditableState,
} from './hooks';
import * as S from './style';

const MODE_ICON = {
  on: {
    icon: PrimaryHighlighterIcon,
    alt: '형광펜 기능 켜짐',
  },
  off: {
    icon: GrayHighlighterIcon,
    alt: '형광펜 기능 꺼짐',
  },
};
export interface HighlightEditorProps {
  questionId: number;
  answerList: ReviewAnswerResponseData[];
  handleErrorModal: (isError: boolean) => void;
}

const HighlightEditor = ({ questionId, answerList, handleErrorModal }: HighlightEditorProps) => {
  const editorRef = useRef<HTMLDivElement>(null);

  const { isEditable, handleEditToggleButton } = useEditableState();

  const { isAddingHighlight, checkHighlight } = useCheckHighlight();

  const { longPressHighlightButtonPosition, hideLongPressHighlightButton, updateLongPressHighlightButtonPosition } =
    useLongPressHighlightButtonPosition({
      isEditable,
      editorRef,
    });

  const { dragHighlightButtonPosition, hideDragHighlightButton, updateDragHighlightButtonPosition } =
    useDragHighlightButtonPosition({
      isEditable,
      editorRef,
      hideLongPressHighlightButton,
    });

  const {
    editorAnswerMap,
    addHighlightByDrag,
    removeHighlightByDrag,
    handleLongPressLine,
    removeHighlightByLongPress,
    removalTarget,
  } = useHighlight({
    questionId,
    answerList,
    isEditable,
    hideDragHighlightButton,
    hideLongPressHighlightButton,
    updateLongPressHighlightButtonPosition,
    handleErrorModal,
  });

  const { startPressTimer, clearPressTimer } = useLongPress({ handleLongPress: handleLongPressLine });

  const hideHighlightButton = (e: MouseEvent | TouchEvent) => {
    if (!isEditable) return;

    const isInButton = (e.target as HTMLElement).closest(`.${HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME}`);
    const isNotHighlightRemover = (e.target as HTMLElement).closest(`.${HIGHLIGHT_REMOVER_CLASS_NAME}`);

    if (!isInButton) hideDragHighlightButton();
    if (!isNotHighlightRemover) hideLongPressHighlightButton();
  };

  const showHighlightButton = () => {
    if (!isEditable) return;
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    const isAddingHighlight = checkHighlight(selectionInfo);
    updateDragHighlightButtonPosition({ selectionInfo, isAddingHighlight });
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

  return (
    <S.HighlightEditor ref={editorRef}>
      <S.SwitchButtonWrapper>
        <S.HighlightText $isEditable={isEditable}>형광펜</S.HighlightText>
        <S.SwitchModIcon
          src={MODE_ICON[isEditable ? 'on' : 'off'].icon}
          alt={MODE_ICON[isEditable ? 'on' : 'off'].alt}
        />
        <EditSwitchButton isEditable={isEditable} handleEditToggleButton={handleEditToggleButton} />
      </S.SwitchButtonWrapper>
      {[...editorAnswerMap.values()].map(({ answerId, answerIndex, lineList }) => (
        <div
          className={EDITOR_ANSWER_CLASS_NAME}
          key={answerId}
          data-answer={`${answerId}-${answerIndex}`}
          onMouseDown={startPressTimer}
          onMouseUp={clearPressTimer}
          onMouseMove={clearPressTimer}
          onTouchMove={handleLongPressLine}
        >
          {lineList.map((line, index) => (
            <EditorLineBlock key={`${EDITOR_LINE_CLASS_NAME}-${index}`} line={line} lineIndex={index} />
          ))}
        </div>
      ))}

      {isEditable && dragHighlightButtonPosition && (
        <DragHighlightButtonContainer
          buttonPosition={dragHighlightButtonPosition}
          isAddingHighlight={isAddingHighlight}
          addHighlightByDrag={addHighlightByDrag}
          removeHighlightByDrag={removeHighlightByDrag}
        />
      )}
      {isEditable && removalTarget && longPressHighlightButtonPosition && (
        <LongPressHighlightButtonWrapper
          buttonPosition={longPressHighlightButtonPosition}
          removeHighlightByLongPress={removeHighlightByLongPress}
        />
      )}
    </S.HighlightEditor>
  );
};

export default HighlightEditor;
