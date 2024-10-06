import { EditorBlockData, Highlight } from '@/types';

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

interface GetRemovedHighlightListParams {
  blockTextLength: number;
  highlightList: Highlight[];
  start: number; // 지우는 영역 시작점
  end: number; // 지우는 영역 끝나는 지점
}

const getHighlightListAfterPartialRemoval = ({
  blockTextLength,
  highlightList,
  start,
  end,
}: GetRemovedHighlightListParams) => {
  // 일부분을 삭제하는 경우
  const stack = '0'.repeat(blockTextLength).split('');
  // 채우기
  highlightList.forEach(({ start: hStart, end: hEnd }) => {
    for (let i = hStart; i <= hEnd; i++) {
      stack[i] = '1';
    }
  });
  for (let i = start; i <= end; i++) {
    stack[i] = '0';
  }

  return makeHighlightListByConsecutiveOnes(stack);
};

const getHighlightListAfterFullyRemoval = ({
  highlightList,
  start,
  end,
}: Omit<GetRemovedHighlightListParams, 'blockTextLength'>) => {
  return highlightList.filter(({ start: hStart, end: hEnd }) => {
    return hEnd <= start || hStart >= end;
  });
};

/*하이라이트 삭제 함수*/
export const getRemovedHighlightList = (params: GetRemovedHighlightListParams) => {
  const { highlightList, start, end } = params;
  const isDeleteHighlightFully = highlightList.find((item) => item.start === start && item.end === end);
  // 이미 있는 하이라이트 영역을 모두 삭제 경우
  if (isDeleteHighlightFully) {
    return getHighlightListAfterFullyRemoval({ highlightList, start, end });
  }

  return getHighlightListAfterPartialRemoval(params);
};
