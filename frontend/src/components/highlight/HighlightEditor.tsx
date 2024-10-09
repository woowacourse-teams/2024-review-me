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

import { Button } from '../common';

import EditorBlock from './EditorBlock';
import HighlightRemoverWrapper from './HighlightRemoverWrapper';
import HighlightToggleButtonContainer from './HighlightToggleButtonContainer';

interface HighlightEditorProps {
  answerList: EditorAnswerData[];
}

const HighlightEditor = ({ answerList }: HighlightEditorProps) => {
  const editorRef = useRef<HTMLDivElement>(null);
  const [isAbleEdit, setIsAbleEdit] = useState(false);

  const handleEditToggleButton = () => {
    setIsAbleEdit((prev) => !prev);
  };

  const { highlightToggleButtonPosition, hideHighlightToggleButton, updateHighlightToggleButtonPosition } =
    useHighlightToggleButtonPosition({
      isAbleEdit,
      editorRef,
    });

  const { removerPosition, hideRemover, updateRemoverPosition } = useHighlightRemoverPosition({
    isAbleEdit,
  });

  const { editorAnswerMap, addHighlight, removeHighlight, handleClickBlockList, handleClickRemover, removalTarget } =
    useHighlight({
      answerList,
      isAbleEdit,
      hideHighlightToggleButton,
      hideRemover,
      updateRemoverPosition,
    });
  const { isAddingHighlight, checkHighlight } = useCheckHighlight();

  const handleMouseDown = (e: React.MouseEvent) => {
    if (!isAbleEdit) return;

    const isInButton = (e.target as HTMLElement).closest(`.${HIGHLIGHT__TOGGLE_BUTTON_CLASS_NAME}`);
    const isNotHighlightRemover = (e.target as HTMLElement).closest(`.${HIGHLIGHT_REMOVER_CLASS_NAME}`);

    if (!isInButton) hideHighlightToggleButton();
    if (!isNotHighlightRemover) hideRemover();
  };

  const handleMouseUp = () => {
    if (!isAbleEdit) return;
    const info = findSelectionInfo();
    if (!info) return;
    checkHighlight(info);
    updateHighlightToggleButtonPosition(info);
  };

  return (
    <div ref={editorRef} onMouseUp={handleMouseUp} onMouseDown={handleMouseDown} style={{ position: 'relative' }}>
      <div style={{ display: 'flex' }}>
        <span>형광펜 모드:</span>
        <Button
          styleType={isAbleEdit ? 'secondary' : 'primary'}
          style={{ padding: '0.4rem 1rem' }}
          onClick={handleEditToggleButton}
        >
          {isAbleEdit ? '끄기' : '켜기'}
        </Button>
      </div>

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

      {isAbleEdit && highlightToggleButtonPosition && (
        <HighlightToggleButtonContainer
          buttonPosition={highlightToggleButtonPosition}
          isAddingHighlight={isAddingHighlight}
          addHighlight={addHighlight}
          removeHighlight={removeHighlight}
        />
      )}
      {isAbleEdit && removalTarget && removerPosition && (
        <HighlightRemoverWrapper buttonPosition={removerPosition} handleClickRemover={handleClickRemover} />
      )}
    </div>
  );
};

export default HighlightEditor;
