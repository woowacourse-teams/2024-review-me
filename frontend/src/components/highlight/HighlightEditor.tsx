import React, { useState } from 'react';

import { EDITOR_BLOCK_CLASS_NAME } from '@/constants';
import { useHighlight } from '@/hooks';
import { getSelectionInfo, SelectionInfo } from '@/utils';

import EditorBlock from './EditorBlock';

const HIGHLIGHT_BUTTON_CLASS_NAME = 'highlight-btn';
interface HighlightEditorProps {
  text: string;
}

const HighlightEditor = ({ text }: HighlightEditorProps) => {
  const { blockList, handleClickHighlight, handleClickHighlightRemover } = useHighlight({ text });
  const [buttonPosition, setButtonPosition] = useState<{ top: number; left: number } | null>(null);

  const hideButton = (e: React.MouseEvent) => {
    const isInButton = (e.target as HTMLElement).closest(`.${HIGHLIGHT_BUTTON_CLASS_NAME}`);

    if (isInButton) return;
    setButtonPosition(null);
  };

  const calculateEndPosition = ({ selection, isForwardDrag, startBlock }: SelectionInfo) => {
    const range = selection.getRangeAt(0);
    const rects = range.getClientRects();

    if (rects.length === 0) return;

    // 드래그 방향에 따른 마지막 rect의 좌표 정보를 가져옴 (마우스가 놓인 최종 지점)
    const lastRect = rects[isForwardDrag ? rects.length - 1 : 0];
    const GAP_WIDTH_SELECTION = 10;
    const endPosition = {
      left: isForwardDrag ? lastRect.right + GAP_WIDTH_SELECTION : lastRect.left - GAP_WIDTH_SELECTION,
      top: lastRect.top - (isForwardDrag ? 0 : startBlock.clientHeight),
    };

    return endPosition;
  };
  const handleMouseUp = () => {
    const info = getSelectionInfo();
    if (!info) return;

    const endPosition = calculateEndPosition(info);
    if (!endPosition) return console.error('endPosition을 찾을 수 없어요.');

    setButtonPosition(endPosition);
  };

  return (
    <div className="highlight-editor" onMouseUp={handleMouseUp} onMouseDown={hideButton}>
      {blockList.map((block, index) => (
        <EditorBlock key={`${EDITOR_BLOCK_CLASS_NAME}-${index}`} block={block} blockIndex={index} />
      ))}
      {buttonPosition && (
        <div className={HIGHLIGHT_BUTTON_CLASS_NAME} style={{ position: 'fixed', ...buttonPosition }}>
          <button onClick={handleClickHighlight}>Add</button>
          <button onClick={handleClickHighlightRemover}>Delete</button>
        </div>
      )}
    </div>
  );
};

export default HighlightEditor;
