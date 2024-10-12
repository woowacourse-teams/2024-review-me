import { EDITOR_ANSWER_CLASS_NAME, EDITOR_LINE_CLASS_NAME } from '@/constants';
import { EditorLine } from '@/types';

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
  anchorLineData: BlockData;
  focusLineData: BlockData;
  anchorOffset: number;
  focusOffset: number;
}
export const getAnswerInfo = ({ anchorLineData, focusLineData, anchorOffset, focusOffset }: GetAnswerInfoParams) => {
  const anchorAnswerElement = anchorLineData.block.closest(`.${EDITOR_ANSWER_CLASS_NAME}`);
  const focusAnswerElement = focusLineData.block.closest(`.${EDITOR_ANSWER_CLASS_NAME}`);

  if (!anchorAnswerElement || !focusAnswerElement) return;

  const anchorAnswerData = getAnswerElementInfo(anchorAnswerElement);
  const focusAnswerData = getAnswerElementInfo(focusAnswerElement);

  if (!anchorAnswerData || !focusAnswerData) return;

  const isSameAnswer = anchorAnswerData.id === focusAnswerData.id;
  // 드래그 방향 계산
  const sortedAnswerData = [anchorAnswerData, focusAnswerData].sort((a, b) => a.index - b.index);
  const isForwardDragAnswer = sortedAnswerData[0].id === anchorAnswerData.id;

  const startAnswer = isForwardDragAnswer
    ? { ...anchorAnswerData, lineIndex: Number(anchorLineData.index), offset: anchorOffset }
    : { ...focusAnswerData, lineIndex: Number(focusLineData.index), offset: focusOffset };

  const endAnswer = isForwardDragAnswer
    ? { ...focusAnswerData, lineIndex: Number(focusLineData.index), offset: focusOffset - 1 }
    : { ...anchorAnswerData, lineIndex: Number(anchorLineData.index), offset: anchorOffset - 1 };

  return {
    isSameAnswer,
    startAnswer,
    endAnswer,
    isForwardDragAnswer,
  };
};

/**
 * anchorNode, focusNode가 있는 element(Line) 정보를 찾는 함수
 * @param selection
 * @returns
 */
export const findSelectedLineInfo = (selection: Selection) => {
  const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;
  const anchorLine = anchorNode?.parentElement?.closest(`.${EDITOR_LINE_CLASS_NAME}`);
  const focusLine = focusNode?.parentElement?.closest(`.${EDITOR_LINE_CLASS_NAME}`);

  if (!anchorLine || !focusLine) return;

  const anchorLineIndex = Number(anchorLine.getAttribute('data-index') || '-1');
  const focusLineIndex = Number(focusLine.getAttribute('data-index') || '-1');

  const answerInfo = getAnswerInfo({
    anchorLineData: { block: anchorLine, index: anchorLineIndex },
    focusLineData: { block: focusLine, index: focusLineIndex },
    anchorOffset,
    focusOffset,
  });

  return {
    anchorLine,
    anchorLineIndex,
    focusLine,
    focusLineIndex,
    ...answerInfo,
  };
};

export type SelectedLineInfo = Exclude<ReturnType<typeof findSelectedLineInfo>, undefined>;

export const calculateStartAndEndLine = ({
  anchorLine,
  anchorLineIndex,
  focusLine,
  focusLineIndex,
}: SelectedLineInfo) => {
  const startLineIndex = Math.min(anchorLineIndex, focusLineIndex);
  const endLineIndex = Math.max(anchorLineIndex, focusLineIndex);
  const startLine = startLineIndex === anchorLineIndex ? anchorLine : focusLine;
  const endLine = startLineIndex === anchorLineIndex ? focusLine : anchorLine;

  return {
    startLine,
    startLineIndex,
    endLine,
    endLineIndex,
  };
};

interface CalculateDragDirectionParams {
  selection: Selection;
  startLineIndex: number;
  endLineIndex: number;
  anchorLineIndex: number;
  isSameAnswer: boolean;
  isForwardDragAnswer: boolean;
}

export const calculateDragDirection = ({
  selection,
  startLineIndex,
  endLineIndex,
  anchorLineIndex,
  isSameAnswer,
  isForwardDragAnswer,
}: CalculateDragDirectionParams) => {
  const { anchorOffset, focusOffset } = selection;
  const minOffset = Math.min(anchorOffset, focusOffset);

  if (isSameAnswer) {
    const isForwardDrag =
      startLineIndex === endLineIndex ? minOffset === anchorOffset : startLineIndex === anchorLineIndex;

    return isForwardDrag;
  }

  return isForwardDragAnswer;
};

/**
 * 하이라이트 추가/삭제에 필요한 selection 관련 정보를 반환하는 함수
 * @returns
 */
export const findSelectionInfo = () => {
  const selection = document.getSelection();
  if (!selection || selection.isCollapsed) return;

  const selectedElementInfo = findSelectedLineInfo(selection);
  if (!selectedElementInfo) return;
  const { isSameAnswer } = selectedElementInfo;
  const { startLine, startLineIndex, endLine, endLineIndex } = calculateStartAndEndLine(selectedElementInfo);

  const isForwardDrag = calculateDragDirection({
    selection,
    startLineIndex,
    endLineIndex,
    anchorLineIndex: selectedElementInfo.anchorLineIndex,
    isSameAnswer: !!isSameAnswer,
    isForwardDragAnswer: !!selectedElementInfo.isForwardDragAnswer,
  });

  const isOnlyOneSelectedBlock = startLineIndex === endLineIndex;

  return {
    selection,
    startLine,
    endLine,
    startLineIndex,
    endLineIndex,
    isForwardDrag,
    isOnlyOneSelectedBlock,
    ...selectedElementInfo,
  };
};

export type SelectionInfo = Exclude<ReturnType<typeof findSelectionInfo>, undefined>;

export const getStartLineOffset = (infoForOffset: SelectionInfo, block: EditorLine) => {
  const { isForwardDrag, startLine, selection, isOnlyOneSelectedBlock } = infoForOffset;
  const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;

  const startIndex = calculateOffsetInBlock({
    selectionTargetNode: isForwardDrag ? anchorNode : focusNode,
    selectionTargetOffset: isForwardDrag ? anchorOffset : focusOffset,
    blockElement: startLine,
  });
  // NOTE: endIndex에 -1하는 이유 : 끝나는 포커스위치의 offset이 글자 index보다 1큼
  const endIndex = isOnlyOneSelectedBlock
    ? calculateOffsetInBlock({
        selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
        selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
        blockElement: startLine,
      })
    : block.text.length;

  return { startIndex, endIndex };
};

export const getEndLineOffset = (infoForOffset: SelectionInfo) => {
  const { isForwardDrag, endLine, selection } = infoForOffset;
  const { anchorNode, anchorOffset, focusNode, focusOffset } = selection;

  const endIndex = calculateOffsetInBlock({
    selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
    selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
    blockElement: endLine,
  });

  return endIndex;
};

export const removeSelection = () => document.getSelection()?.removeAllRanges();
