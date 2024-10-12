import { useEffect, useRef, useState } from 'react';

import GrayHighlighterIcon from '@/assets/grayHighlighter.svg';
import PrimaryHighlighterIcon from '@/assets/primaryHighlighter.svg';
import {
  EDITOR_ANSWER_CLASS_NAME,
  EDITOR_LINE_CLASS_NAME,
  HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME,
  HIGHLIGHT_REMOVER_CLASS_NAME,
} from '@/constants';
import { useDragHighlightButtonPosition, useHighlight, useCheckHighlight, useHighlightRemoverPosition } from '@/hooks';
import { ReviewAnswerResponseData } from '@/types';
import { findSelectionInfo } from '@/utils';

import DragHighlightButtonContainer from '../DragHighlightButtonContainer';
import EditorLineBlock from '../EditorLineBlock';
import EditSwitchButton from '../EditSwitchButton';
import HighlightRemoverWrapper from '../HighlightRemoverWrapper';

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
interface HighlightEditorProps {
  questionId: number;
  answerList: ReviewAnswerResponseData[];
}

const HighlightEditor = ({ questionId, answerList }: HighlightEditorProps) => {
  const editorRef = useRef<HTMLDivElement>(null);
  const [isEditable, setIsEditable] = useState(false);
  const { isAddingHighlight, checkHighlight } = useCheckHighlight();

  const handleEditToggleButton = () => {
    setIsEditable((prev) => !prev);
  };

  const { dragHighlightButtonPosition, hideDragHighlightButton, updateDragHighlightButtonPosition } =
    useDragHighlightButtonPosition({
      isEditable,
      editorRef,
    });

  const { removerPosition, hideRemover, updateRemoverPosition } = useHighlightRemoverPosition({
    isEditable,
    editorRef,
  });

  const {
    editorAnswerMap,
    addHighlightByDrag,
    removeHighlightByDrag,
    handleClickBlockList,
    removeHighlightByClick,
    removalTarget,
  } = useHighlight({
    questionId,
    answerList,
    isEditable,
    hideDragHighlightButton,
    hideRemover,
    updateRemoverPosition,
  });

  const handleMouseDown = (e: MouseEvent) => {
    if (!isEditable) return;

    const isInButton = (e.target as HTMLElement).closest(`.${HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME}`);
    const isNotHighlightRemover = (e.target as HTMLElement).closest(`.${HIGHLIGHT_REMOVER_CLASS_NAME}`);

    if (!isInButton) hideDragHighlightButton();
    if (!isNotHighlightRemover) hideRemover();
  };

  const handleMouseUp = () => {
    if (!isEditable) return;
    const selectionInfo = findSelectionInfo();
    if (!selectionInfo) return;

    const isAddingHighlight = checkHighlight(selectionInfo);
    updateDragHighlightButtonPosition({ selectionInfo, isAddingHighlight });
  };

  useEffect(() => {
    document.addEventListener('mouseup', handleMouseUp);
    document.addEventListener('mousedown', handleMouseDown);
    return () => {
      document.removeEventListener('mouseup', handleMouseUp);
      document.removeEventListener('mousedown', handleMouseDown);
    };
  }, [isEditable]);

  return (
    <S.HighlightEditorContainer ref={editorRef}>
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
          onClick={handleClickBlockList}
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
      {isEditable && removalTarget && removerPosition && (
        <HighlightRemoverWrapper buttonPosition={removerPosition} removeHighlightByClick={removeHighlightByClick} />
      )}
    </S.HighlightEditorContainer>
  );
};

export default HighlightEditor;
