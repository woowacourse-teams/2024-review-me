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

const HighlightEditor = () => {
  const [blockList, setBlockList] = useState<Block[]>(() =>
    MOCK_DATA.split('\n').map((text) => ({
      text,
      highlightList: [],
    })),
  );

  /**
   * 하나의 블럭에 대한 highlightList에 새로운 하이라이트를 추가하는 함수
   * (기존의 하이라이트와 곁치는 영역이 있으면 하나의 하이라이트로 병합)
   * @param highlightList
   * @param newHighlight
   * @returns
   */
  const mergeHighlightList = (highlightList: Highlight[], newHighlight: Highlight) => {
    const merged = [...highlightList];
    let hasMerged = false;

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

  /**
   * 하이라이트에 따라, 블록의 글자를 하이라이트 적용되는 부분과 그렇지 않은 부분으로 나누는 함수
   */
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

    const highlightedTextList = splitTextWithHighlights(text, highlightList);
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

  // 선택된 텍스트의 오프셋을 계산하는 함수
  const getSelectionOffsetInBlock = (
    selection: Selection,
    blockElement: Element,
  ): { start: number; end: number } | null => {
    const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;

    const anchorBlock = anchorNode?.parentElement?.closest('.block');
    const focusBlock = focusNode?.parentElement?.closest('.block');
    if (!anchorBlock || !focusBlock) return null;

    let totalOffset = 0;
    const allSpans = Array.from(blockElement.querySelectorAll('span'));

    for (const span of allSpans) {
      if (span.contains(anchorNode)) {
        totalOffset += anchorOffset;
        break;
      }
      totalOffset += span.textContent?.length || 0;
    }

    const start = totalOffset;

    totalOffset = 0;
    for (const span of allSpans) {
      if (span.contains(focusNode)) {
        totalOffset += focusOffset;
        break;
      }
      totalOffset += span.textContent?.length || 0;
    }

    const end = totalOffset;
    return { start: Math.min(start, end), end: Math.max(start, end) };
  };

  const updateBlockListByHighlight = (blockIndex: number, start: number, end: number) => {
    const newBlocks = [...blockList];
    const newHighlight = { start, length: end - start };

    newBlocks[blockIndex] = {
      ...newBlocks[blockIndex],
      highlightList: mergeHighlightList(newBlocks[blockIndex].highlightList, newHighlight),
    };

    setBlockList(newBlocks);
  };

  // 하이라이트 삭제 함수
  const getRemovedHighlightList = (highlights: Highlight[], start: number, end: number) => {
    return highlights.filter(({ start: hStart, length }) => {
      const hEnd = hStart + length;
      return hEnd <= start || hStart >= end;
    });
  };

  const handleClickHighlight = () => {
    const selection = document.getSelection();
    if (!selection || selection.isCollapsed) return;

    const anchorBlock = selection.anchorNode?.parentElement?.closest('.block');
    const focusBlock = selection.focusNode?.parentElement?.closest('.block');
    if (!anchorBlock || !focusBlock) return;

    const blockIndex = parseInt(anchorBlock.getAttribute('data-index') || '-1', 10);
    const offsets = getSelectionOffsetInBlock(selection, anchorBlock);
    if (!offsets) return;

    updateBlockListByHighlight(blockIndex, offsets.start, offsets.end);
  };

  const handleClickHighlightRemover = () => {
    const selection = document.getSelection();
    if (!selection || selection.isCollapsed) return;

    const anchorBlock = selection.anchorNode?.parentElement?.closest('.block');
    const focusBlock = selection.focusNode?.parentElement?.closest('.block');
    if (!anchorBlock || !focusBlock) return;

    const blockIndex = parseInt(anchorBlock.getAttribute('data-index') || '-1', 10);
    const offsets = getSelectionOffsetInBlock(selection, anchorBlock);
    if (!offsets) return;

    const newBlocks = [...blockList];
    newBlocks[blockIndex] = {
      ...newBlocks[blockIndex],
      highlightList: getRemovedHighlightList(newBlocks[blockIndex].highlightList, offsets.start, offsets.end),
    };

    setBlockList(newBlocks);
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
