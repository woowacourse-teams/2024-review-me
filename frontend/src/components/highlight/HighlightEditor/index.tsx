import React, { useRef, useState } from 'react';

import {
  EDITOR_ANSWER_CLASS_NAME,
  EDITOR_BLOCK_CLASS_NAME,
  HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME,
  HIGHLIGHT_REMOVER_CLASS_NAME,
} from '@/constants';
import {
  useHighlightToggleButtonPosition,
  useHighlight,
  useCheckHighlight,
  useHighlightRemoverPosition,
} from '@/hooks';
import { EditorAnswerData } from '@/types';
import { findSelectionInfo } from '@/utils';

import EditorBlock from '../EditorBlock';
import EditSwitchButton from '../EditSwitchButton';
import HighlightRemoverWrapper from '../HighlightRemoverWrapper';
import HighlightToggleButtonContainer from '../HighlightToggleButtonContainer';

import * as S from './style';

interface HighlightEditorProps {
  answerList: EditorAnswerData[];
}

const HighlightEditor = ({ answerList }: HighlightEditorProps) => {
  const editorRef = useRef<HTMLDivElement>(null);
  const [isEditAble, setIsEditAble] = useState(false);

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

  const { editorAnswerMap, addHighlight, removeHighlight, handleClickBlockList, handleClickRemover, removalTarget } =
    useHighlight({
      answerList,
      isEditAble,
      hideHighlightToggleButton,
      hideRemover,
      updateRemoverPosition,
    });
  const { isAddingHighlight, checkHighlight } = useCheckHighlight();

  const handleMouseDown = (e: React.MouseEvent) => {
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
    checkHighlight(info);
    updateHighlightToggleButtonPosition(info);
  };

  return (
    <div ref={editorRef} onMouseUp={handleMouseUp} onMouseDown={handleMouseDown} style={{ position: 'relative' }}>
      <S.SwitchButtonWrapper>
        <EditSwitchButton isEditAble={isEditAble} handleEditToggleButton={handleEditToggleButton} />
      </S.SwitchButtonWrapper>
      {[...editorAnswerMap.values()].map(({ answerId, answerIndex, blockList }) => (
        <div
          className={EDITOR_ANSWER_CLASS_NAME}
          key={answerId}
          data-answer={`${answerId}-${answerIndex}`}
          onClick={handleClickBlockList}
        >
          {blockList.map((block, index) => (
            <EditorBlock key={`${EDITOR_BLOCK_CLASS_NAME}-${index}`} block={block} blockIndex={index} />
          ))}
        </div>
      ))}

      {isEditAble && highlightToggleButtonPosition && (
        <HighlightToggleButtonContainer
          buttonPosition={highlightToggleButtonPosition}
          isAddingHighlight={isAddingHighlight}
          addHighlight={addHighlight}
          removeHighlight={removeHighlight}
        />
      )}
      {isEditAble && removalTarget && removerPosition && (
        <HighlightRemoverWrapper buttonPosition={removerPosition} handleClickRemover={handleClickRemover} />
      )}
    </div>
  );
};

export default HighlightEditor;
