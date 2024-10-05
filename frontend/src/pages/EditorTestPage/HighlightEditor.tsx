import { useState } from 'react';

interface HighlightInfo {
  start: number;
  length: number;
}

interface Block {
  text: string;
  highlightList: HighlightInfo[];
}

const MOCK_DATA =
  '나는 말야, 버릇이 하나있어, 그건 매일 잠에 들 시간마다잘 모아둔 기억 조각들 잡히는 걸 집은 후 혼자 조용히 꼬꼬무\n이걸 난\n이름으로 지었어, 고민,\n아무튼, 뭐, 오늘은 하필이면\n너가 스쳐버려서 우리였을 때로\n우리 정말 좋았던 그때로\n우리의 에피소드가 찬란하게 막을 연다\n배경은 너의 집 앞, 첫 데이트가 끝난\n둘만의 에피소드가 참 예쁜 얘기로 시작\n자작자작, 조심스런 대화, 그새 늦은 시간';

const HighlightEditor = () => {
  const [blocks, setBlocks] = useState<Block[]>(() => {
    return MOCK_DATA.split('\n').map((text) => ({
      text,
      highlightList: [],
    }));
  });

  const getHightedInfo = (block: Block) => {
    const { text, highlightList } = block;

    const list: { isHighlight: boolean; text: string }[] = [];

    highlightList.forEach(({ start, length }, index) => {
      if (index === 0) {
        if (highlightList.length === 1) {
          const beforeHighlight = text.slice(0, start);
          const highlightedText = text.slice(start, start + length);
          const afterHighlight = text.slice(start + length);
          !!beforeHighlight && list.push({ isHighlight: false, text: beforeHighlight });
          list.push({ isHighlight: true, text: highlightedText });
          !!afterHighlight && list.push({ isHighlight: false, text: afterHighlight });

          return;
        }

        const string = text.slice(0, length + 1);
        const beforeHighlight = string.slice(0, start);
        const highlightedText = string.slice(start);

        !!beforeHighlight && list.push({ isHighlight: false, text: beforeHighlight });
        list.push({ isHighlight: true, text: highlightedText });
        return;
      }

      if (index === highlightList.length - 1) {
        const previous = highlightList[index - 1];
        const string = text.slice(previous.start + previous.length);
        const beforeHighlight = string.slice(0, start);
        const highlightedText = string.slice(start, start + length);
        const afterHighlight = string.slice(start + length);
        !!beforeHighlight && list.push({ isHighlight: false, text: beforeHighlight });
        list.push({ isHighlight: true, text: highlightedText });
        !!afterHighlight && list.push({ isHighlight: false, text: afterHighlight });
        return;
      }

      const previous = highlightList[index - 1];
      const string = text.slice(previous.start + previous.length, start + length);
      const beforeHighlight = string.slice(0, start);
      const highlightedText = string.slice(start, start + length);
      !!beforeHighlight && list.push({ isHighlight: false, text: beforeHighlight });
      list.push({ isHighlight: true, text: highlightedText });
    });

    return list;
  };

  const renderBlock = (block: Block, index: number) => {
    if (!block.highlightList.length) {
      return <span key={index}>{block.text}</span>;
    }

    const list = getHightedInfo(block);
    return (
      <>
        {list.map(({ isHighlight, text }, listIndex) => (
          <span
            key={`block-${index}__${listIndex}`}
            style={{ backgroundColor: isHighlight ? '#E6E3F6' : 'transparent' }}
          >
            {text}
          </span>
        ))}
      </>
    );
  };

  const handleClick = () => {
    const selection = document.getSelection();

    if (!selection) return;
    if (selection.isCollapsed) return;

    const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;
    const anchorBlock = anchorNode?.parentElement?.closest('.block');
    const focusBlock = focusNode?.parentElement?.closest('.block');

    if (!anchorBlock) return;
    if (!focusBlock) return;

    const anchorBlockIndex = parseInt(anchorBlock.getAttribute('data-index') || '-1', 10);
    const focusBlockIndex = parseInt(focusBlock.getAttribute('data-index') || '-1', 10);
    const startBlockIndex = Math.min(anchorBlockIndex, focusBlockIndex);
    const endBlockIndex = Math.max(anchorBlockIndex, focusBlockIndex);

    const newBlocks = [...blocks];

    if (startBlockIndex === endBlockIndex) {
      const startOffset = Math.min(anchorOffset, focusOffset);
      const endOffset = Math.max(anchorOffset, focusOffset);
      newBlocks[startBlockIndex] = {
        ...newBlocks[startBlockIndex],
        highlightList: [{ start: startOffset, length: endOffset - startOffset }],
      };
      setBlocks(newBlocks);
      return;
    }
    const startOffset = startBlockIndex === anchorBlockIndex ? anchorOffset : focusOffset;
    const endOffset = startBlockIndex === anchorBlockIndex ? focusOffset : anchorOffset;

    for (let i = startBlockIndex; i <= endBlockIndex; i++) {
      if (i === startBlockIndex) {
        newBlocks[i] = {
          ...newBlocks[i],
          highlightList: [{ start: startOffset, length: newBlocks[i].text.length - startOffset }],
        };
      }

      if (i === endBlockIndex) {
        newBlocks[i] = {
          ...newBlocks[i],
          highlightList: [{ start: 0, length: endOffset }],
        };
      }

      if (i !== startBlockIndex && i !== endBlockIndex) {
        newBlocks[i] = {
          ...newBlocks[i],
          highlightList: [{ start: 0, length: newBlocks[i].text.length }],
        };
      }
    }
    console.log('new', newBlocks);
    setBlocks(newBlocks);
  };

  return (
    <div className="highlight-editor">
      {blocks.map((block, index) => (
        <div key={index} className="block" data-index={index}>
          {renderBlock(block, index)}
        </div>
      ))}
      <button onClick={handleClick}>Toggle Highlight on first block</button>
    </div>
  );
};

export default HighlightEditor;
