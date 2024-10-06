import { EDITOR_BLOCK_CLASS_NAME } from '@/constants';
import { Highlight, EditorBlockData } from '@/types';

/**
 * '0','1'로 이루어진 배열을 가지고, highlightList를 만드는 함수
 * 1이 하나 이상일 경우, 시작 index가 start 이고 연속이 끝나는 index가 end
 * @param array
 * @returns
 */
const makeHighlightListByConsecutiveOnes = (array: string[]) => {
  const result = [];
  let start = -1; // 시작점 초기화 (아직 찾지 못한 상태)

  for (let i = 0; i < array.length; i++) {
    if (array[i] === '1' && start === -1) {
      // 1이 시작되는 지점
      start = i;
    } else if ((array[i] === '0' || i === array.length - 1) && start !== -1) {
      // 1이 끝나는 지점: 0을 만났거나 배열의 끝에 도달했을 때
      const end = array[i] === '1' ? i : i - 1;
      result.push({ start, end });
      start = -1; // 다시 초기화
    }
  }

  return result;
};

interface MergeHighlightListParams {
  blockTextLength: number;
  highlightList: Highlight[];
  newHighlight: Highlight;
}
export const mergeHighlightList = ({
  blockTextLength,
  highlightList,
  newHighlight,
}: MergeHighlightListParams): Highlight[] => {
  const stack = '0'.repeat(blockTextLength).split('');

  [...highlightList, newHighlight].forEach((item) => {
    const { start, end } = item;
    for (let i = start; i <= end; i++) {
      stack[i] = '1';
    }
  });

  return makeHighlightListByConsecutiveOnes(stack);
};

interface GetSelectionOffsetInBlockParams {
  selectionTargetNode: Node | null;
  selectionTargetOffset: number;
  blockElement: Element;
}
/*
 *선택된 텍스트의 block 기준 offset을 계산하는 함수
 */
export const getSelectionOffsetInBlock = ({
  selectionTargetNode,
  selectionTargetOffset,
  blockElement,
}: GetSelectionOffsetInBlockParams) => {
  const spanIndex = selectionTargetNode?.parentElement?.getAttribute('data-index');

  if (!spanIndex) {
    console.error(`${selectionTargetNode}에 대한 span의 data-index를 찾을 수 없습니다.`);
    return 0;
  }

  const spanList = [...blockElement.querySelectorAll('span')];
  const offset =
    spanList.slice(0, Number(spanIndex)).reduce((acc, cur) => acc + (cur.textContent?.length || 0), 0) +
    selectionTargetOffset;
  return offset;
};

interface GetUpdatedBlockByHighlightParams {
  blockTextLength: number;
  blockIndex: number;
  start: number;
  end: number;
  blockList: EditorBlockData[];
}

export const getUpdatedBlockByHighlight = ({
  blockTextLength,
  blockIndex,
  start,
  end,
  blockList,
}: GetUpdatedBlockByHighlightParams) => {
  const newHighlight = { start, end };
  const block = blockList[blockIndex];
  const { highlightList } = block;

  return {
    ...block,
    highlightList: mergeHighlightList({ blockTextLength, highlightList, newHighlight }),
  };
};

interface GetRemovedHighlightParams {
  blockTextLength: number;
  highlightList: Highlight[];
  start: number; // 지우는 영역 시작점
  end: number; // 지우는 영역 끝나는 지점
}

/*하이라이트 삭제 함수*/
export const getRemovedHighlightList = ({ blockTextLength, highlightList, start, end }: GetRemovedHighlightParams) => {
  const isDeleteHighlightFully = highlightList.find((item) => item.start === start && item.end === end);
  // 이미 있는 하이라이트 영역을 모두 삭제 경우
  if (isDeleteHighlightFully) {
    return highlightList.filter(({ start: hStart, end: hEnd }) => {
      return hEnd <= start || hStart >= end;
    });
  }
  // 일부분을 삭제하는 경우
  const stack = '0'.repeat(blockTextLength).split('');
  // 채우기
  highlightList.forEach(({ start: hStart, end: hEnd }) => {
    for (let i = hStart; i <= hEnd; i++) {
      stack[i] = '1';
    }
  });
  // 지우기
  for (let i = start; i <= end; i++) {
    stack[i] = '0';
  }

  return makeHighlightListByConsecutiveOnes(stack);
};

export const getSelectionOffsetBlockInfo = (selection: Selection) => {
  const { anchorNode, focusNode } = selection;
  const anchorBlock = anchorNode?.parentElement?.closest(`.${EDITOR_BLOCK_CLASS_NAME}`);
  const focusBlock = focusNode?.parentElement?.closest(`.${EDITOR_BLOCK_CLASS_NAME}`);

  if (!anchorBlock || !focusBlock) return;

  const anchorBlockIndex = parseInt(anchorBlock.getAttribute('data-index') || '-1', 10);
  const focusBlockIndex = parseInt(focusBlock.getAttribute('data-index') || '-1', 10);

  return {
    anchorBlock,
    anchorBlockIndex,
    focusBlock,
    focusBlockIndex,
  };
};

export type SelectionBlockInfo = Exclude<ReturnType<typeof getSelectionOffsetBlockInfo>, undefined>;

export const calculateStartAndEndBlock = ({
  anchorBlock,
  anchorBlockIndex,
  focusBlock,
  focusBlockIndex,
}: SelectionBlockInfo) => {
  const startBlockIndex = Math.min(anchorBlockIndex, focusBlockIndex);
  const endBlockIndex = Math.max(anchorBlockIndex, focusBlockIndex);
  const startBlock = startBlockIndex === anchorBlockIndex ? anchorBlock : focusBlock;
  const endBlock = startBlockIndex === anchorBlockIndex ? focusBlock : anchorBlock;

  return {
    startBlock,
    startBlockIndex,
    endBlock,
    endBlockIndex,
  };
};

interface CalculateDragDirectionParams {
  selection: Selection;
  startBlockIndex: number;
  endBlockIndex: number;
  anchorBlockIndex: number;
}

export const calculateDragDirection = ({
  selection,
  startBlockIndex,
  endBlockIndex,
  anchorBlockIndex,
}: CalculateDragDirectionParams) => {
  const { anchorOffset, focusOffset } = selection;
  const minOffset = Math.min(anchorOffset, focusOffset);

  const isForwardDrag =
    startBlockIndex === endBlockIndex ? minOffset === anchorOffset : startBlockIndex === anchorBlockIndex;

  return {
    isForwardDrag,
  };
};

export const getSelectionInfo = () => {
  const selection = document.getSelection();
  if (!selection || selection.isCollapsed) return;

  const blockInfo = getSelectionOffsetBlockInfo(selection);
  if (!blockInfo) return;

  const { startBlock, startBlockIndex, endBlock, endBlockIndex } = calculateStartAndEndBlock(blockInfo);

  const { isForwardDrag } = calculateDragDirection({
    selection,
    startBlockIndex,
    endBlockIndex,
    anchorBlockIndex: blockInfo.anchorBlockIndex,
  });

  return {
    selection,
    startBlock,
    endBlock,
    startBlockIndex,
    endBlockIndex,
    isForwardDrag,
  };
};

export type SelectionInfo = Exclude<ReturnType<typeof getSelectionInfo>, undefined>;
export const removeSelection = () => document.getSelection()?.removeAllRanges();
