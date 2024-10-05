import { useState } from 'react';

import {
  getRemovedHighlightList,
  getSelectionInfo,
  getSelectionOffsetInBlock,
  getUpdatedBlockByHighlight,
  splitTextWithHighlightList,
} from '@/utils';

interface Highlight {
  start: number;
  length: number;
}

interface Block {
  text: string;
  highlightList: Highlight[];
}

interface HighlightEditorProps {
  text: string;
}

const HighlightEditor = ({ text }: HighlightEditorProps) => {
  const [blockList, setBlockList] = useState<Block[]>(() =>
    text.split('\n').map((text) => ({
      text,
      highlightList: [],
    })),
  );

  /**
   * 블록의 값에 따라 하이라이트 여부를 반영한 태그들을 렌더링하는 함수
   * @param block
   * @param index block의 index
   */
  const renderBlock = (block: Block, index: number) => {
    const { text, highlightList } = block;
    const key = `block-${index}__span`;
    if (!highlightList.length) {
      return <span key={key}>{text}</span>;
    }

    const highlightedTextList = splitTextWithHighlightList({ text, highlightList });
    return (
      <>
        {highlightedTextList.map(({ isHighlight, text }, i) => (
          <span key={`${key}${i}`} data-index={i} style={{ backgroundColor: isHighlight ? '#E6E3F6' : 'transparent' }}>
            {text}
          </span>
        ))}
      </>
    );
  };

  const handleClickHighlight = () => {
    const info = getSelectionInfo();
    if (!info) return;
    const { selection, startBlock, startBlockIndex, endBlock, endBlockIndex } = info;

    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const startOffset = getSelectionOffsetInBlock({ selection, blockElement: startBlock });
        return getUpdatedBlockByHighlight({ blockIndex: index, ...startOffset, blockList });
      }
      if (index === endBlockIndex) {
        const endOffset = getSelectionOffsetInBlock({ selection, blockElement: endBlock });
        return getUpdatedBlockByHighlight({ blockIndex: index, start: 0, end: endOffset.start, blockList });
      }
      return {
        ...block,
        highlightList: [{ start: 0, length: block.text.length }],
      };
    });

    setBlockList(newBlockList);
  };

  const handleClickHighlightRemover = () => {
    const info = getSelectionInfo();
    if (!info) return;
    const { selection, startBlock, startBlockIndex, endBlock, endBlockIndex } = info;

    const newBlockList = blockList.map((block, index) => {
      if (index < startBlockIndex) return block;
      if (index > endBlockIndex) return block;
      if (index === startBlockIndex) {
        const startOffset = getSelectionOffsetInBlock({ selection, blockElement: startBlock });
        return {
          ...block,
          highlightList: getRemovedHighlightList({ highlightList: block.highlightList, ...startOffset }),
        };
      }
      if (index === endBlockIndex) {
        const endOffset = getSelectionOffsetInBlock({ selection, blockElement: endBlock });
        return {
          ...block,
          highlightList: getRemovedHighlightList({
            highlightList: block.highlightList,
            start: 0,
            end: endOffset.start,
          }),
        };
      }
      return {
        ...block,
        highlightList: [],
      };
    });

    setBlockList(newBlockList);
  };

  return (
    <div className="highlight-editor">
      {blockList.map((block, index) => (
        <div key={index} className="block" data-index={index}>
          {renderBlock(block, index)}
        </div>
      ))}
      <button onClick={handleClickHighlight}>Add Highlight</button>
      <button onClick={handleClickHighlightRemover}>Delete Highlight</button>
    </div>
  );
};

export default HighlightEditor;
