import { useRef } from 'react';

import DotIcon from '@/assets/dot.svg';
import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { EDITOR_ANSWER_CLASS_NAME, EDITOR_LINE_CLASS_NAME } from '@/constants';
import { ReviewAnswerResponseData } from '@/types';

import EditorLineBlock from '../EditorLineBlock';
import EditSwitchButton from '../EditSwitchButton';
import HighlightMenu from '../HighlightMenu';
import Tooltip from '../Tooltip';

import { useHighlight, useCheckHighlight, useLongPress, useEditableState, useHighlightEventListener } from './hooks';
import useHighlightMenuPosition from './hooks/useHighlightMenuPosition';
import * as S from './style';

export interface HighlightEditorProps {
  questionId: number;
  answerList: ReviewAnswerResponseData[];
  handleErrorModal: (isError: boolean) => void;
}

const HighlightEditor = ({ questionId, answerList, handleErrorModal }: HighlightEditorProps) => {
  const editorRef = useRef<HTMLDivElement>(null);

  const { isEditable, handleEditToggleButton } = useEditableState();

  const { highlightArea, checkHighlight } = useCheckHighlight();

  const {
    menuPosition,
    updateHighlightMenuPositionByDrag,
    updateHighlightMenuPositionByLongPress,
    resetHighlightMenuPosition,
  } = useHighlightMenuPosition({
    editorRef,
    isEditable,
  });

  const {
    editorAnswerMap,
    longPressRemovalTarget,
    addHighlightByDrag,
    removeHighlightByDrag,
    handleLongPressLine,
    removeHighlightByLongPress,
    resetLongPressRemovalTarget,
  } = useHighlight({
    questionId,
    answerList,
    isEditable,
    resetHighlightMenuPosition,
    updateHighlightMenuPositionByLongPress,
    handleErrorModal,
  });

  const { startPressTimer, clearPressTimer } = useLongPress({ handleLongPress: handleLongPressLine });

  useHighlightEventListener({
    isEditable,
    updateHighlightMenuPositionByDrag,
    resetHighlightMenuPosition,
    resetLongPressRemovalTarget,
    checkHighlight,
  });

  return (
    <S.HighlightEditor ref={editorRef}>
      <S.SwitchButtonWrapper>
        <S.HighlightText>형광펜</S.HighlightText>
        <Tooltip />
        <EditSwitchButton isEditable={isEditable} handleEditToggleButton={handleEditToggleButton} />
      </S.SwitchButtonWrapper>
      <ul>
        {[...editorAnswerMap.values()].map(({ answerId, answerIndex, lineList }) => (
          <S.AnswerListItem
            className={EDITOR_ANSWER_CLASS_NAME}
            key={answerId}
            data-answer={`${answerId}-${answerIndex}`}
            onMouseDown={startPressTimer}
            onMouseUp={clearPressTimer}
            onMouseMove={clearPressTimer}
            onTouchMove={handleLongPressLine}
          >
            <UndraggableWrapper>
              <S.Marker src={DotIcon} alt="점" />
            </UndraggableWrapper>
            <S.AnswerText>
              {lineList.map((line, index) => (
                <EditorLineBlock key={`${EDITOR_LINE_CLASS_NAME}-${index}`} line={line} lineIndex={index} />
              ))}
            </S.AnswerText>
          </S.AnswerListItem>
        ))}
      </ul>
      {isEditable && menuPosition && (
        <HighlightMenu
          position={menuPosition}
          highlightArea={highlightArea}
          isOpenLongPressRemove={!!longPressRemovalTarget}
          addHighlightByDrag={addHighlightByDrag}
          removeHighlightByDrag={removeHighlightByDrag}
          removeHighlightByLongPress={removeHighlightByLongPress}
        />
      )}
    </S.HighlightEditor>
  );
};

export default HighlightEditor;
