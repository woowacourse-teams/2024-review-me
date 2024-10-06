import React from 'react';

import { EDITOR_BLOCK_CLASS_NAME, HIGHLIGHT_BUTTON_CLASS_NAME } from '@/constants';
import { useHighlightButtonPosition, useHighlight } from '@/hooks';
import { getSelectionInfo } from '@/utils';

import EditorBlock from './EditorBlock';

interface HighlightEditorProps {
  text: string;
}

const HighlightEditor = ({ text }: HighlightEditorProps) => {
  const { highlightButtonPosition, hideHighlightButton, updateHighlightButtonPosition } = useHighlightButtonPosition();
  const { blockList, handleClickHighlight, handleClickHighlightRemover } = useHighlight({ text, hideHighlightButton });

  const handleMouseDown = (e: React.MouseEvent) => {
    const isInButton = (e.target as HTMLElement).closest(`.${HIGHLIGHT_BUTTON_CLASS_NAME}`);

    if (isInButton) return;
    hideHighlightButton();
  };

  const handleMouseUp = () => {
    const info = getSelectionInfo();
    if (!info) return;
    updateHighlightButtonPosition(info);
  };

  return (
    <div onMouseUp={handleMouseUp} onMouseDown={handleMouseDown}>
      {blockList.map((block, index) => (
        <EditorBlock key={`${EDITOR_BLOCK_CLASS_NAME}-${index}`} block={block} blockIndex={index} />
      ))}
      {highlightButtonPosition && (
        <div className={HIGHLIGHT_BUTTON_CLASS_NAME} style={{ position: 'fixed', ...highlightButtonPosition }}>
          <button onClick={handleClickHighlight}>Add</button>
          <button onClick={handleClickHighlightRemover}>Delete</button>
        </div>
      )}
    </div>
  );
};

export default HighlightEditor;
