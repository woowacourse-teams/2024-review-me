import { useEffect, useRef, useState } from 'react';

import {
  EDITOR_ANSWER_CLASS_NAME,
  EDITOR_LINE_CLASS_NAME,
  HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME,
  HIGHLIGHT_REMOVER_CLASS_NAME,
} from '@/constants';
import {
  useHighlightToggleButtonPosition,
  useHighlight,
  useCheckHighlight,
  useHighlightRemoverPosition,
} from '@/hooks';
import { ReviewAnswerResponseData } from '@/types';
import { findSelectionInfo } from '@/utils';

import EditorLineBlock from '../EditorLineBlock';
import EditSwitchButton from '../EditSwitchButton';
import HighlightRemoverWrapper from '../HighlightRemoverWrapper';
import HighlightToggleButtonContainer from '../HighlightToggleButtonContainer';

import * as S from './style';

interface HighlightEditorProps {
  questionId: number;
  answerList: ReviewAnswerResponseData[];
}

const HighlightEditor = ({ questionId, answerList }: HighlightEditorProps) => {
  const editorRef = useRef<HTMLDivElement>(null);
  const [isEditAble, setIsEditAble] = useState(false);
  const { isAddingHighlight, checkHighlight } = useCheckHighlight();

  const handleEditToggleButton = () => {
    setIsEditAble((prev) => !prev);
  };

  const { highlightToggleButtonPosition, hideHighlightToggleButton, updateHighlightToggleButtonPosition } =
    useHighlightToggleButtonPosition({
      isEditAble,
      editorRef,
    });

  const { removerPosition, hideRemover, updateRemoverPosition } = useHighlightRemoverPosition({
    isEditAble,
    editorRef,
  });

  const {
    editorAnswerMap,
    addHighlight,
    removeHighlightByDrag,
    handleClickBlockList,
    removeHighlightByClick,
    removalTarget,
  } = useHighlight({
    questionId,
    answerList,
    isEditAble,
    hideHighlightToggleButton,
    hideRemover,
    updateRemoverPosition,
  });

  const handleMouseDown = (e: MouseEvent) => {
    if (!isEditAble) return;

    const isInButton = (e.target as HTMLElement).closest(`.${HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME}`);
    const isNotHighlightRemover = (e.target as HTMLElement).closest(`.${HIGHLIGHT_REMOVER_CLASS_NAME}`);

    if (!isInButton) hideHighlightToggleButton();
    if (!isNotHighlightRemover) hideRemover();
  };

  const handleMouseUp = () => {
    if (!isEditAble) return;
    const info = findSelectionInfo();
    if (!info) return;

    const isAddingHighlight = checkHighlight(info);
    updateHighlightToggleButtonPosition({ info, isAddingHighlight });
  };

  useEffect(() => {
    document.addEventListener('mouseup', handleMouseUp);
    document.addEventListener('mousedown', handleMouseDown);
    return () => {
      document.removeEventListener('mouseup', handleMouseUp);
      document.removeEventListener('mousedown', handleMouseDown);
    };
  }, [isEditAble]);

  return (
    <div ref={editorRef} style={{ position: 'relative' }}>
      <S.SwitchButtonWrapper>
        <EditSwitchButton isEditAble={isEditAble} handleEditToggleButton={handleEditToggleButton} />
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

      {isEditAble && highlightToggleButtonPosition && (
        <HighlightToggleButtonContainer
          buttonPosition={highlightToggleButtonPosition}
          isAddingHighlight={isAddingHighlight}
          addHighlight={addHighlight}
          removeHighlightByDrag={removeHighlightByDrag}
        />
      )}
      {isEditAble && removalTarget && removerPosition && (
        <HighlightRemoverWrapper buttonPosition={removerPosition} removeHighlightByClick={removeHighlightByClick} />
      )}
    </div>
  );
};

export default HighlightEditor;
