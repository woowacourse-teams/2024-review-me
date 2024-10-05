import { Highlight, EditorBlock } from '@/types';

interface MergeHighlightListParams {
  highlightList: Highlight[];
  newHighlight: Highlight;
}
export const mergeHighlightList = ({ highlightList, newHighlight }: MergeHighlightListParams) => {
  const merged = [...highlightList];
  let hasMerged = false;

  for (let i = 0; i < merged.length; i++) {
    const current = merged[i];
    if (
      newHighlight.start <= current.start + current.length &&
      newHighlight.start + newHighlight.length >= current.start
    ) {
      const start = Math.min(current.start, newHighlight.start);
      const end = Math.max(current.start + current.length, newHighlight.start + newHighlight.length);
      merged[i] = { start, length: end - start };
      hasMerged = true;
      break;
    }
  }

  if (!hasMerged) {
    merged.push(newHighlight);
  }

  return merged.sort((a, b) => a.start - b.start);
};

interface SplitTextWithHighlightListParams {
  text: string;
  highlightList: Highlight[];
}
/**
 * 하이라이트에 따라, 블록의 글자를 하이라이트 적용되는 부분과 그렇지 않은 부분으로 나누는 함수
 */
export const splitTextWithHighlightList = ({ text, highlightList }: SplitTextWithHighlightListParams) => {
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

interface GetSelectionOffsetInBlockParams {
  selection: Selection;
  blockElement: Element;
}
/*
 *선택된 텍스트의 block 기준 offset을 계산하는 함수
 */
export const getSelectionOffsetInBlock = ({ selection, blockElement }: GetSelectionOffsetInBlockParams) => {
  const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;

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

interface GetUpdatedBlockByHighlightParams {
  blockIndex: number;
  start: number;
  end: number;
  blockList: EditorBlock[];
}

export const getUpdatedBlockByHighlight = ({ blockIndex, start, end, blockList }: GetUpdatedBlockByHighlightParams) => {
  const newHighlight = { start, length: end - start };
  const block = blockList[blockIndex];
  const { highlightList } = block;

  return {
    ...block,
    highlightList: mergeHighlightList({ highlightList, newHighlight }),
  };
};

interface GetRemovedHighlightParams {
  highlightList: Highlight[];
  start: number;
  end: number;
}

/*하이라이트 삭제 함수*/
export const getRemovedHighlightList = ({ highlightList, start, end }: GetRemovedHighlightParams) => {
  const isDeleteHighlightFully = highlightList.find((item) => item.start === start && item.length === start + end);
  // 이미 있는 하이라이트 영역을 모두 삭제 경우
  if (isDeleteHighlightFully) {
    return highlightList.filter(({ start: hStart, length }) => {
      const hEnd = hStart + length;
      return hEnd <= start || hStart >= end;
    });
  }
  // 일부분을 삭제하는 경우
  const newHighlightList: Highlight[] = [];

  highlightList.forEach(({ start: hStart, length }) => {
    const hEnd = hStart + length;
    //제거되는 하이라이트가 아닌 경우
    if (hEnd <= start || hStart >= end) {
      newHighlightList.push({ start: hStart, length });
    }
    //제거되는 하이라이트인 경우
    if (hStart < start) {
      newHighlightList.push({ start: hStart, length: start - hStart });
    }
    if (hEnd > end) {
      newHighlightList.push({ start: end, length: hEnd - end });
    }
  });

  return newHighlightList;
};

export const getSelectionInfo = () => {
  const selection = document.getSelection();
  if (!selection || selection.isCollapsed) return;

  const anchorBlock = selection.anchorNode?.parentElement?.closest('.block');
  const focusBlock = selection.focusNode?.parentElement?.closest('.block');
  if (!anchorBlock || !focusBlock) return;

  const anchorBlockIndex = parseInt(anchorBlock.getAttribute('data-index') || '-1', 10);
  const focusBlockIndex = parseInt(focusBlock.getAttribute('data-index') || '-1', 10);
  const startBlockIndex = Math.min(anchorBlockIndex, focusBlockIndex);
  const endBlockIndex = Math.max(anchorBlockIndex, focusBlockIndex);
  const startBlock = startBlockIndex === anchorBlockIndex ? anchorBlock : focusBlock;
  const endBlock = startBlockIndex === anchorBlockIndex ? focusBlock : anchorBlock;

  return {
    startBlock,
    endBlock,
    startBlockIndex,
    endBlockIndex,
    selection,
  };
};

export const removeSelection = () => document.getSelection()?.removeAllRanges();
