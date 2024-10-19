import { useRef } from 'react';

import GrayHighlighterIcon from '@/assets/grayHighlighter.svg';
import PrimaryHighlighterIcon from '@/assets/primaryHighlighter.svg';
import { EDITOR_ANSWER_CLASS_NAME, EDITOR_LINE_CLASS_NAME } from '@/constants';
import { ReviewAnswerResponseData } from '@/types';

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
  useHighlightEventListener,
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

  useHighlightEventListener({
    isEditable,
    updateDragHighlightButtonPosition,
    hideDragHighlightButton,
    hideLongPressHighlightButton,
    checkHighlight,
  });

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
      <S.EditorAnswerContainer>
        {[...editorAnswerMap.values()].map(({ answerId, answerIndex, lineList }) => (
          <S.EditorAnswerItem
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
          </S.EditorAnswerItem>
        ))}
      </S.EditorAnswerContainer>

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
