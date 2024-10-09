import { EditorBlockData, Highlight } from '@/types';

interface CreateHighlightBinaryArrayParams {
  arrayLength: number;
  list: Highlight[];
}
/**
 * 하이라이트 적용 여부를 이진법에 따라 표시하는 배열을 생성하는 함수
 * @param list 배열에 표시할 하이라이트 배열
 * @param arrayLength 이진법 배열의 length이자 하이라이트 적용 대상인 block의 글자 수
 */
const createHighlightBinaryArray = ({ arrayLength, list }: CreateHighlightBinaryArrayParams) => {
  const array = '0'.repeat(arrayLength).split('');

  list.forEach((item) => {
    const { startIndex, endIndex } = item;
    for (let i = startIndex; i <= endIndex; i++) {
      array[i] = '1';
    }
  });

  return array;
};
/**
 * '0','1'로 이루어진 배열을 가지고, highlightList를 만드는 함수
 * 1이 하나 이상일 경우, 시작 index가 start 이고 연속이 끝나는 index가 end
 * @param array
 */
const makeHighlightListByConsecutiveOnes = (array: string[]) => {
  const result: Highlight[] = [];
  let startIndex = -1; // 시작점 초기화 (아직 찾지 못한 상태)

  for (let i = 0; i < array.length; i++) {
    if (array[i] === '1' && startIndex === -1) {
      // 1이 시작되는 지점
      startIndex = i;
    } else if ((array[i] === '0' || i === array.length - 1) && startIndex !== -1) {
      // 1이 끝나는 지점: 0을 만났거나 배열의 끝에 도달했을 때
      const endIndex = array[i] === '1' ? i : i - 1;
      result.push({ startIndex, endIndex });
      startIndex = -1; // 다시 초기화
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
  const array = createHighlightBinaryArray({ arrayLength: blockTextLength, list: highlightList.concat(newHighlight) });

  return makeHighlightListByConsecutiveOnes(array);
};

interface GetUpdatedBlockByHighlightParams {
  blockTextLength: number;
  blockIndex: number;
  startIndex: number;
  endIndex: number;
  blockList: EditorBlockData[];
}

export const getUpdatedBlockByHighlight = ({
  blockTextLength,
  blockIndex,
  startIndex,
  endIndex,
  blockList,
}: GetUpdatedBlockByHighlightParams) => {
  const newHighlight: Highlight = { startIndex, endIndex };
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
  startIndex: number; // 지우는 영역 시작점
  endIndex: number; // 지우는 영역 끝나는 지점
}

/**
 * 이미 있는 하이라이트 중, 일부분을 삭제하고 새로운 highlightList를 반환하는 함수
 */
const getHighlightListAfterPartialRemoval = ({
  blockTextLength,
  highlightList,
  startIndex,
  endIndex,
}: GetRemovedHighlightListParams) => {
  const array = createHighlightBinaryArray({ arrayLength: blockTextLength, list: highlightList });

  //지우기
  for (let i = startIndex; i <= endIndex; i++) {
    array[i] = '0';
  }

  return makeHighlightListByConsecutiveOnes(array);
};

/**
 * 이미 있는 하이라이트 중, 해당 하이라이트를 삭제하고 새로운 highlightList를 반환하는 함수
 */
const getHighlightListAfterFullyRemoval = ({
  highlightList,
  startIndex,
  endIndex,
}: Omit<GetRemovedHighlightListParams, 'blockTextLength'>) => {
  return highlightList.filter(({ startIndex: hStartIndex, endIndex: hEndIndex }) => {
    return hEndIndex <= startIndex || hStartIndex >= endIndex;
  });
};

/*하이라이트 삭제 함수*/
export const getRemovedHighlightList = (params: GetRemovedHighlightListParams) => {
  const { highlightList, startIndex, endIndex } = params;
  const isDeleteHighlightFully = highlightList.find(
    (item) => item.startIndex === startIndex && item.endIndex === endIndex,
  );
  // 이미 있는 하이라이트 영역을 모두 삭제 경우
  if (isDeleteHighlightFully) {
    return getHighlightListAfterFullyRemoval({ highlightList, startIndex, endIndex });
  }

  return getHighlightListAfterPartialRemoval(params);
};
