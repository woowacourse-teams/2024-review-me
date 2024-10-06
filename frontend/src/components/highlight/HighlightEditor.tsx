import React, { useState } from 'react';

import { EDITOR_BLOCK_CLASS_NAME, HIGHLIGHT_BUTTON_CLASS_NAME } from '@/constants';
import { useHighlightButtonPosition, useHighlight, useCheckHighlight } from '@/hooks';
import { findSelectionInfo } from '@/utils';

import EditorBlock from './EditorBlock';

interface HighlightEditorProps {
  text: string;
}

const HighlightEditor = ({ text }: HighlightEditorProps) => {
  const [isAbleEdit, setIsAbleEdit] = useState(false);
  const { highlightButtonPosition, hideHighlightButton, updateHighlightButtonPosition } = useHighlightButtonPosition({
    isAbleEdit,
  });
  const { blockList, handleClickHighlight, handleClickHighlightRemover } = useHighlight({ text, hideHighlightButton });
  const { isAddingHighlight, checkHighlight } = useCheckHighlight();

  const handleMouseDown = (e: React.MouseEvent) => {
    if (!isAbleEdit) return;

    const isInButton = (e.target as HTMLElement).closest(`.${HIGHLIGHT_BUTTON_CLASS_NAME}`);

    if (isInButton) return;
    hideHighlightButton();
  };

  const handleMouseUp = () => {
    if (!isAbleEdit) return;
    const info = findSelectionInfo();
    if (!info) return;

    checkHighlight(info);
    updateHighlightButtonPosition(info);
  };

  const handleToggleButton = () => {
    setIsAbleEdit((prev) => !prev);
  };

  return (
    <div onMouseUp={handleMouseUp} onMouseDown={handleMouseDown}>
      <div>
        <p>형광펜 모드:</p>
        <button onClick={handleToggleButton}> {isAbleEdit ? '끄기' : '켜기'}</button>
      </div>

      {blockList.map((block, index) => (
        <EditorBlock key={`${EDITOR_BLOCK_CLASS_NAME}-${index}`} block={block} blockIndex={index} />
      ))}
      {isAbleEdit && highlightButtonPosition && (
        <div className={HIGHLIGHT_BUTTON_CLASS_NAME} style={{ position: 'fixed', ...highlightButtonPosition }}>
          {isAddingHighlight ? (
            <button onClick={handleClickHighlight}>Add</button>
          ) : (
            <button onClick={handleClickHighlightRemover}>Delete</button>
          )}
        </div>
      )}
    </div>
  );
};

export default HighlightEditor;
