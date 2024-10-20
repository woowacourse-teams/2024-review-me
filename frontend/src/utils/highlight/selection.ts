import { EDITOR_ANSWER_CLASS_NAME, EDITOR_LINE_CLASS_NAME } from '@/constants';
import { EditorLine } from '@/types';

interface GetSelectionOffsetInBlockParams {
  selectionTargetNode: Node | null;
  selectionTargetOffset: number;
  lineElement: Element;
}
/*
 *선택된 텍스트의 Line 기준 offset을 계산하는 함수
 */
export const calculateOffsetInLine = ({
  selectionTargetNode,
  selectionTargetOffset,
  lineElement,
}: GetSelectionOffsetInBlockParams) => {
  const spanIndex = selectionTargetNode?.parentElement?.getAttribute('data-index');

  if (!spanIndex) {
    console.error(`${selectionTargetNode}에 대한 span의 data-index를 찾을 수 없습니다.`);
    return 0;
  }

  const spanList = [...lineElement.querySelectorAll('span')];
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

interface LineData {
  line: Element;
  index: number;
}
interface GetAnswerInfoParams {
  anchorLineData: LineData;
  focusLineData: LineData;
  anchorIndexInLine: number;
  focusIndexInLine: number;
}

export const getAnswerInfo = ({
  anchorLineData,
  focusLineData,
  anchorIndexInLine,
  focusIndexInLine,
}: GetAnswerInfoParams) => {
  const anchorAnswerElement = anchorLineData.line.closest(`.${EDITOR_ANSWER_CLASS_NAME}`);
  const focusAnswerElement = focusLineData.line.closest(`.${EDITOR_ANSWER_CLASS_NAME}`);

  if (!anchorAnswerElement || !focusAnswerElement) return;

  const anchorAnswerData = getAnswerElementInfo(anchorAnswerElement);
  const focusAnswerData = getAnswerElementInfo(focusAnswerElement);

  if (!anchorAnswerData || !focusAnswerData) return;

  const isSameAnswer = anchorAnswerData.id === focusAnswerData.id;
  // 드래그 방향 계산
  const sortedAnswerData = [anchorAnswerData, focusAnswerData].sort((a, b) => a.index - b.index);
  const isForwardDragAnswer = sortedAnswerData[0].id === anchorAnswerData.id;

  const startAnswer = isForwardDragAnswer
    ? { ...anchorAnswerData, lineIndex: Number(anchorLineData.index), offset: anchorIndexInLine }
    : { ...focusAnswerData, lineIndex: Number(focusLineData.index), offset: focusIndexInLine };

  const endAnswer = isForwardDragAnswer
    ? { ...focusAnswerData, lineIndex: Number(focusLineData.index), offset: focusIndexInLine - 1 }
    : { ...anchorAnswerData, lineIndex: Number(anchorLineData.index), offset: anchorIndexInLine - 1 };

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
 */
export const findSelectedLineInfo = (selection: Selection) => {
  const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;
  const anchorLineElement = anchorNode?.parentElement?.closest(`.${EDITOR_LINE_CLASS_NAME}`);
  const focusLineElement = focusNode?.parentElement?.closest(`.${EDITOR_LINE_CLASS_NAME}`);

  if (!anchorLineElement || !focusLineElement) return;

  const anchorLineIndex = Number(anchorLineElement.getAttribute('data-index') || '-1');
  const focusLineIndex = Number(focusLineElement.getAttribute('data-index') || '-1');

  // 줄 기준 Offset 비교
  const anchorIndexInLine = calculateOffsetInLine({
    selectionTargetNode: anchorNode,
    selectionTargetOffset: anchorOffset,
    lineElement: anchorLineElement,
  });

  const focusIndexInLine = calculateOffsetInLine({
    selectionTargetNode: focusNode,
    selectionTargetOffset: focusOffset,
    lineElement: focusLineElement,
  });

  const answerInfo = getAnswerInfo({
    anchorLineData: { line: anchorLineElement, index: anchorLineIndex },
    focusLineData: { line: focusLineElement, index: focusLineIndex },
    anchorIndexInLine,
    focusIndexInLine,
  });

  return {
    anchorLineElement,
    anchorLineIndex,
    focusLineElement,
    focusLineIndex,
    anchorIndexInLine,
    focusIndexInLine,
    ...answerInfo,
  };
};

export type SelectedLineInfo = Exclude<ReturnType<typeof findSelectedLineInfo>, undefined>;

export const calculateStartAndEndLine = ({
  anchorLineElement,
  anchorLineIndex,
  focusLineElement,
  focusLineIndex,
}: SelectedLineInfo) => {
  const startLineIndex = Math.min(anchorLineIndex, focusLineIndex);
  const endLineIndex = Math.max(anchorLineIndex, focusLineIndex);
  const startLineElement = startLineIndex === anchorLineIndex ? anchorLineElement : focusLineElement;
  const endLineElement = startLineIndex === anchorLineIndex ? focusLineElement : anchorLineElement;

  return {
    startLineElement,
    startLineIndex,
    endLineElement,
    endLineIndex,
  };
};

interface CalculateDragDirectionParams {
  startLineIndex: number;
  endLineIndex: number;
  anchorIndexInLine: number;
  focusIndexInLine: number;
  isSameAnswer: boolean;
  isForwardDragAnswer: boolean;
}

export const calculateDragDirection = ({
  startLineIndex,
  endLineIndex,
  anchorIndexInLine,
  focusIndexInLine,
  isSameAnswer,
  isForwardDragAnswer,
}: CalculateDragDirectionParams) => {
  // 하이라이트 영역의 시작과 끝이 다른 답변일 경우
  if (!isSameAnswer) return isForwardDragAnswer;

  // 하이라이트 영역의 시작과 끝이 같은 답변의 같은 줄인 경우
  const isSameLine = startLineIndex === endLineIndex;

  // 같은 답변의 같은 줄
  if (isSameLine) return anchorIndexInLine < focusIndexInLine;

  // 같은 답변의 다른 줄
  return startLineIndex < endLineIndex;
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
  const { isSameAnswer, anchorIndexInLine, focusIndexInLine } = selectedElementInfo;
  const { startLineElement, startLineIndex, endLineElement, endLineIndex } =
    calculateStartAndEndLine(selectedElementInfo);

  const isForwardDrag = calculateDragDirection({
    startLineIndex,
    endLineIndex,
    focusIndexInLine,
    anchorIndexInLine,
    isSameAnswer: !!isSameAnswer,
    isForwardDragAnswer: !!selectedElementInfo.isForwardDragAnswer,
  });

  const isOnlyOneSelectedBlock = startLineIndex === endLineIndex;

  return {
    selection,
    startLineElement,
    endLineElement,
    startLineIndex,
    endLineIndex,
    isForwardDrag,
    isOnlyOneSelectedBlock,
    ...selectedElementInfo,
  };
};

export type SelectionInfo = Exclude<ReturnType<typeof findSelectionInfo>, undefined>;

export const getStartLineOffset = (infoForOffset: SelectionInfo, line: EditorLine) => {
  const { isForwardDrag, startLineElement, selection, isOnlyOneSelectedBlock } = infoForOffset;
  const { anchorNode, focusNode, anchorOffset, focusOffset } = selection;
  const startIndex = calculateOffsetInLine({
    selectionTargetNode: isForwardDrag ? anchorNode : focusNode,
    selectionTargetOffset: isForwardDrag ? anchorOffset : focusOffset,
    lineElement: startLineElement,
  });
  // NOTE: endIndex에 -1하는 이유 : 끝나는 포커스위치의 offset이 글자 index보다 1큼
  const endIndex = isOnlyOneSelectedBlock
    ? calculateOffsetInLine({
        selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
        selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
        lineElement: startLineElement,
      })
    : line.text.length - 1;

  return { startIndex, endIndex };
};

export const getEndLineOffset = (infoForOffset: SelectionInfo) => {
  const { isForwardDrag, endLineElement, selection } = infoForOffset;
  const { anchorNode, anchorOffset, focusNode, focusOffset } = selection;

  const endIndex = calculateOffsetInLine({
    selectionTargetNode: isForwardDrag ? focusNode : anchorNode,
    selectionTargetOffset: isForwardDrag ? focusOffset - 1 : anchorOffset - 1,
    lineElement: endLineElement,
  });

  return endIndex;
};

export const removeSelection = () => document.getSelection()?.removeAllRanges();
