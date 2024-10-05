import { useState } from 'react';

interface Highlight {
  start: number;
  length: number;
}

interface Block {
  text: string;
  highlightList: Highlight[];
}

const MOCK_DATA =
  '나는 말야, 버릇이 하나있어, 그건 매일 잠에 들 시간마다잘 모아둔 기억 조각들 잡히는 걸 집은 후 혼자 조용히 꼬꼬무\n이걸 난\n이름으로 지었어, 고민,\n아무튼, 뭐, 오늘은 하필이면\n너가 스쳐버려서 우리였을 때로\n우리 정말 좋았던 그때로\n우리의 에피소드가 찬란하게 막을 연다\n배경은 너의 집 앞, 첫 데이트가 끝난\n둘만의 에피소드가 참 예쁜 얘기로 시작\n자작자작, 조심스런 대화, 그새 늦은 시간';

const mergeHighlights = (highlightList: Highlight[], newHighlight: Highlight) => {
  const merged = [...highlightList];
  let hasMerged = false;
  console.log('new', newHighlight);
  // 새로운 하이라이트가 기존과 겹치는지 확인
  for (let i = 0; i < merged.length; i++) {
    const current = merged[i];
    if (
      newHighlight.start <= current.start + current.length &&
      newHighlight.start + newHighlight.length >= current.start
    ) {
      // 겹친다면 하이라이트 병합
      const start = Math.min(current.start, newHighlight.start);
      const end = Math.max(current.start + current.length, newHighlight.start + newHighlight.length);
      merged[i] = { start, length: end - start };
      hasMerged = true;
      break;
    }
  }

  // 기존 하이라이트와 겹치지 않으면 새로 추가
  if (!hasMerged) {
    merged.push(newHighlight);
  }

  // 하이라이트 리스트 정렬
  return merged.sort((a, b) => a.start - b.start);
};

const HighlightEditor = () => {
  const [blocks, setBlocks] = useState<Block[]>(() =>
    MOCK_DATA.split('\n').map((text) => ({
      text,
      highlightList: [],
    })),
  );

  const splitTextWithHighlights = (text: string, highlightList: Highlight[]) => {
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
          <span
            key={`block-${index}__${i}`}
            data-index={i}
            style={{ backgroundColor: isHighlight ? '#E6E3F6' : 'transparent' }}
          >
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
      const newHighlight = { start: start, length: end - start };
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
    const anchorSpan = anchorNode?.parentElement;
    const anchorBlock = anchorSpan?.closest('.block');
    const focusSpan = focusNode?.parentElement;
    const focusBlock = focusSpan?.closest('.block');
    if (!anchorSpan || !anchorBlock || !focusSpan || !focusBlock) return;
    const anchorSpanIndex = parseInt(anchorSpan.getAttribute('data-index') || '-1', 10);
    const anchorBlockIndex = parseInt(anchorBlock.getAttribute('data-index') || '-1', 10);
    const focusSpanIndex = parseInt(focusSpan.getAttribute('data-index') || '-1', 10);
    const focusBlockIndex = parseInt(focusBlock.getAttribute('data-index') || '-1', 10);

    const totalAnchorOffset =
      [...anchorBlock.querySelectorAll('span')]
        .slice(0, anchorSpanIndex)
        .reduce((acc, cur) => acc + (cur.textContent?.length || 0), 0) + anchorOffset;

    const totalFocusOffset =
      [...focusBlock.querySelectorAll('span')]
        .slice(0, focusSpanIndex)
        .reduce((acc, cur) => acc + (cur.textContent?.length || 0), 0) + focusOffset;

    if (anchorBlockIndex === -1 || focusBlockIndex === -1) return;

    const startBlockIndex = Math.min(anchorBlockIndex, focusBlockIndex);
    const endBlockIndex = Math.max(anchorBlockIndex, focusBlockIndex);

    updateHighlights(startBlockIndex, endBlockIndex, totalAnchorOffset, totalFocusOffset);
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
