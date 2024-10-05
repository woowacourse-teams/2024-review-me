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
  const [blocks, setBlocks] = useState<Block[]>(() =>
    MOCK_DATA.split('\n').map((text) => ({
      text,
      highlightList: [],
    })),
  );

  const splitTextWithHighlights = (text: string, highlightList: HighlightInfo[]) => {
    const result: { isHighlight: boolean; text: string }[] = [];
    let currentIndex = 0;

    highlightList.forEach(({ start, length }) => {
      if (currentIndex < start) {
        result.push({ isHighlight: false, text: text.slice(currentIndex, start) });
      }
      result.push({ isHighlight: true, text: text.slice(start, start + length) });
      currentIndex = start + length;
    });

    if (currentIndex < text.length) {
      result.push({ isHighlight: false, text: text.slice(currentIndex) });
    }

    return result;
  };

  const renderBlock = (block: Block, index: number) => {
    const { text, highlightList } = block;
    if (!highlightList.length) {
      return <span key={index}>{text}</span>;
    }

    const highlightedTextList = splitTextWithHighlights(text, highlightList);
    return (
      <>
        {highlightedTextList.map(({ isHighlight, text }, i) => (
          <span key={`block-${index}__${i}`} style={{ backgroundColor: isHighlight ? '#E6E3F6' : 'transparent' }}>
            {text}
          </span>
        ))}
      </>
    );
  };

  const updateHighlights = (
    startBlockIndex: number,
    endBlockIndex: number,
    anchorOffset: number,
    focusOffset: number,
  ) => {
    const newBlocks = [...blocks];
    const startOffset = Math.min(anchorOffset, focusOffset);
    const endOffset = Math.max(anchorOffset, focusOffset);

    for (let i = startBlockIndex; i <= endBlockIndex; i++) {
      const start = i === startBlockIndex ? startOffset : 0;
      const end = i === endBlockIndex ? endOffset : newBlocks[i].text.length;

      // 하이라이트 병합 로직 적용
      const newHighlight = { start, length: end - start };
      newBlocks[i] = {
        ...newBlocks[i],
        highlightList: mergeHighlights(newBlocks[i].highlightList, newHighlight),
      };
    }

    setBlocks(newBlocks);
  };

  const handleClick = () => {
    const selection = document.getSelection();
    if (!selection || selection.isCollapsed) return;

    const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;
    const anchorBlock = anchorNode?.parentElement?.closest('.block');
    const focusBlock = focusNode?.parentElement?.closest('.block');
    if (!anchorBlock || !focusBlock) return;

    const anchorBlockIndex = parseInt(anchorBlock.getAttribute('data-index') || '-1', 10);
    const focusBlockIndex = parseInt(focusBlock.getAttribute('data-index') || '-1', 10);
    if (anchorBlockIndex === -1 || focusBlockIndex === -1) return;

    const startBlockIndex = Math.min(anchorBlockIndex, focusBlockIndex);
    const endBlockIndex = Math.max(anchorBlockIndex, focusBlockIndex);

    updateHighlights(startBlockIndex, endBlockIndex, anchorOffset, focusOffset);
  };

  return (
    <div className="highlight-editor">
      {blocks.map((block, index) => (
        <div key={index} className="block" data-index={index}>
          {renderBlock(block, index)}
        </div>
      ))}
      <button onClick={handleClick}>Toggle Highlight</button>
    </div>
  );
};

export default HighlightEditor;
