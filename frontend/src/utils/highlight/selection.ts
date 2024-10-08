import { EDITOR_ANSWER_CLASS_NAME, EDITOR_BLOCK_CLASS_NAME } from '@/constants';
import { EditorBlockData } from '@/types';

interface GetSelectionOffsetInBlockParams {
  selectionTargetNode: Node | null;
  selectionTargetOffset: number;
  blockElement: Element;
}
/*
 *선택된 텍스트의 block 기준 offset을 계산하는 함수
 */
export const calculateOffsetInBlock = ({
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

const getAnswerElementInfo = (element: Element) => {
  const info = element
    .getAttribute('data-answer')
    ?.split('-')
    .reduce(
      (acc, cur, index) => {
        if (index === 0) acc.id = Number(cur);
        if (index === 1) acc.index = Number(cur);
        return acc;
      },
      { id: 0, index: 0 },
    );

  return info;
};

interface BlockData {
  block: Element;
  index: number;
}
interface GetAnswerInfoParams {
  anchorBlockData: BlockData;
  focusBlockData: BlockData;
  anchorOffset: number;
  focusOffset: number;
}
export const getAnswerInfo = ({ anchorBlockData, focusBlockData, anchorOffset, focusOffset }: GetAnswerInfoParams) => {
  const anchorAnswerElement = anchorBlockData.block.closest(`.${EDITOR_ANSWER_CLASS_NAME}`);
  const focusAnswerElement = focusBlockData.block.closest(`.${EDITOR_ANSWER_CLASS_NAME}`);

  if (!anchorAnswerElement || !focusAnswerElement) return;

  const anchorAnswerData = getAnswerElementInfo(anchorAnswerElement);
  const focusAnswerData = getAnswerElementInfo(focusAnswerElement);

  if (!anchorAnswerData || !focusAnswerData) return;

  const isSameAnswer = anchorAnswerData.id === focusAnswerData.id;
  // 드래그 방향 계산
  const sortedAnswerData = [anchorAnswerData, focusAnswerData].sort((a, b) => a.index - b.index);
  const isForwardDrag = sortedAnswerData[0].id === anchorAnswerData.id;

  const startAnswer = isForwardDrag
    ? { id: anchorAnswerData.id, blockIndex: Number(anchorBlockData.index), offset: anchorOffset }
    : { id: focusAnswerData.id, blockIndex: Number(focusBlockData.index), offset: focusOffset };

  const endAnswer = isForwardDrag
    ? { id: focusAnswerData.id, blockIndex: Number(focusBlockData.index), offset: focusOffset - 1 }
    : { id: anchorAnswerData.id, blockIndex: Number(anchorBlockData.index), offset: anchorOffset - 1 };

  return {
    isSameAnswer,
    startAnswer,
    endAnswer,
  };
};

/**
 * anchorNode, focusNode가 있는 block 정보를 찾는 함수
 * @param selection
 * @returns
 */
export const findSelectedElementInfo = (selection: Selection) => {
  const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;
  const anchorBlock = anchorNode?.parentElement?.closest(`.${EDITOR_BLOCK_CLASS_NAME}`);
  const focusBlock = focusNode?.parentElement?.closest(`.${EDITOR_BLOCK_CLASS_NAME}`);

  if (!anchorBlock || !focusBlock) return;

  const anchorBlockIndex = parseInt(anchorBlock.getAttribute('data-index') || '-1', 10);
  const focusBlockIndex = parseInt(focusBlock.getAttribute('data-index') || '-1', 10);

  const answerInfo = getAnswerInfo({
    anchorBlockData: { block: anchorBlock, index: anchorBlockIndex },
    focusBlockData: { block: focusBlock, index: focusBlockIndex },
    anchorOffset,
    focusOffset,
  });

  return {
    anchorBlock,
    anchorBlockIndex,
    focusBlock,
    focusBlockIndex,
    ...answerInfo,
  };
};

export type SelectedBlockInfo = Exclude<ReturnType<typeof findSelectedElementInfo>, undefined>;

export const calculateStartAndEndBlock = ({
  anchorBlock,
  anchorBlockIndex,
  focusBlock,
  focusBlockIndex,
}: SelectedBlockInfo) => {
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

/**
 * 하이라이트 추가/삭제에 필요한 selection 관련 정보를 반환하는 함수
 * @returns
 */
export const findSelectionInfo = () => {
  const selection = document.getSelection();
  if (!selection || selection.isCollapsed) return;

  const selectedElementInfo = findSelectedElementInfo(selection);
  if (!selectedElementInfo) return;

  const { startBlock, startBlockIndex, endBlock, endBlockIndex } = calculateStartAndEndBlock(selectedElementInfo);

  const { isForwardDrag } = calculateDragDirection({
    selection,
    startBlockIndex,
    endBlockIndex,
    anchorBlockIndex: selectedElementInfo.anchorBlockIndex,
  });

  const isOnlyOneSelectedBlock = startBlockIndex === endBlockIndex;

  return {
    selection,
    startBlock,
    endBlock,
    startBlockIndex,
    endBlockIndex,
    isForwardDrag,
    isOnlyOneSelectedBlock,
    ...selectedElementInfo,
  };
};

export type EditorSelectionInfo = Exclude<ReturnType<typeof findSelectionInfo>, undefined>;

export const getStartBlockOffset = (infoForOffset: EditorSelectionInfo, block: EditorBlockData) => {
  const { isForwardDrag, startBlock, selection, isOnlyOneSelectedBlock } = infoForOffset;
  const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;

  const start = calculateOffsetInBlock({
    selectionTargetNode: isForwardDrag ? anchorNode : focusNode,
    selectionTargetOffset: isForwardDrag ? anchorOffset : focusOffset,
    blockElement: startBlock,
  });
  // NOTE: end에 -1하는 이유 : 끝나는 포커스위치의 offset이 글자 index보다 1큼
  const end = isOnlyOneSelectedBlock
    ? calculateOffsetInBlock({
        selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
        selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
        blockElement: startBlock,
      })
    : block.text.length;

  return { start, end };
};

export const getEndBlockOffset = (infoForOffset: EditorSelectionInfo) => {
  const { isForwardDrag, endBlock, selection } = infoForOffset;
  const { anchorNode, anchorOffset, focusNode, focusOffset } = selection;

  const end = calculateOffsetInBlock({
    selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
    selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
    blockElement: endBlock,
  });

  return end;
};

export const removeSelection = () => document.getSelection()?.removeAllRanges();
