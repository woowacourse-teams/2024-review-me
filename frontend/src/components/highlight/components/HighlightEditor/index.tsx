import { useRef } from 'react';

import DotIcon from '@/assets/dot.svg';
import GrayHighlighterIcon from '@/assets/grayHighlighter.svg';
import PrimaryHighlighterIcon from '@/assets/primaryHighlighter.svg';
import { EDITOR_ANSWER_CLASS_NAME, EDITOR_LINE_CLASS_NAME } from '@/constants';
import { ReviewAnswerResponseData } from '@/types';

import EditorLineBlock from '../EditorLineBlock';
import EditSwitchButton from '../EditSwitchButton';
import HighlightMenu from '../HighlightMenu';

import { useHighlight, useCheckHighlight, useLongPress, useEditableState, useHighlightEventListener } from './hooks';
import useHighlightMenuPosition from './hooks/useHighlightMenuPosition';
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

  const { highlightArea, checkHighlight } = useCheckHighlight();

  const { menuPosition, updateHighlightMenuPositionByDrag, updateHighlightMenuPositionByLongPress, hideHighlightMenu } =
    useHighlightMenuPosition({
      editorRef,
      isEditable,
    });

  const {
    editorAnswerMap,
    addHighlightByDrag,
    removeHighlightByDrag,
    handleLongPressLine,
    removeHighlightByLongPress,
  } = useHighlight({
    questionId,
    answerList,
    isEditable,
    hideHighlightMenu,
    updateHighlightMenuPositionByLongPress,
    handleErrorModal,
  });

  const { startPressTimer, clearPressTimer } = useLongPress({ handleLongPress: handleLongPressLine });

  useHighlightEventListener({
    isEditable,
    updateHighlightMenuPositionByDrag,
    hideHighlightMenu,
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
            <S.Marker src={DotIcon} alt="점" />
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
          isOpenLongPressRemove={false}
          addHighlightByDrag={addHighlightByDrag}
          removeHighlightByDrag={removeHighlightByDrag}
          removeHighlightByLongPress={removeHighlightByLongPress}
        />
      )}
    </S.HighlightEditor>
  );
};

export default HighlightEditor;
