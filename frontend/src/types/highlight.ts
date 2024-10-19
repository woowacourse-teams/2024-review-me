/**
 * 하이라이트가 적용된 구문에서 하이라이트가 시작되는 지점과 끝점
 */
export interface HighlightRange {
  startIndex: number;
  endIndex: number;
}

// NOTE: 서버에서는 하이라이트가 적용된 문장에 대한 하이라이트 정보만 보내주지만, 클라이언트는 뷰에 하이라이트 적용여부를 보여주는 편의성을 위핸 모든 문장 index를 가지지만, highlightList가 빈배열이면 하이아리트 적용이 안되어있고 빈배열이 아니면 하이라이트가 있습니다

// 서버에서 내려주고, 받는 데이터 타입
/**
 * 하이라이트가 적용된 block(=문장)의 index, 하이라이트 범위 배열을 가진 타입
 */
export interface Highlight {
  lineIndex: number; // 하이라이트가 적용된 문장 index
  // 서버에서는 ranges로 줌
  rangeList: HighlightRange[];
}
// 서버에서 보내주는 리뷰 모아보기 데이터 속 하이라이트 타입
export interface HighlightResponseData {
  lineIndex: number;
  ranges: HighlightRange[];
}
// 서버에서 보내주는 리뷰 모아보기 데이터
export interface ReviewAnswerResponseData {
  id: number;
  content: string;
  highlights: HighlightResponseData[];
}

/**
 * 하이라이트 변경 시, 서버에 보내는 하이라이트 정보 타입
 */
export interface HighlightPostPayload {
  questionId: number;
  highlights: {
    answerId: number;
    //하이라이트가 적용된 블럭의 정보를 보내줌
    lines: {
      index: number; // 하이라이트가 적용된 구문의 index
      ranges: HighlightRange[];
    }[];
  }[];
}
// 클라이언트에서 사용하는 형광펜 대상 주관식 답변 타입
export interface EditorAnswer {
  content: string;
  answerId: number;
  answerIndex: number;
  lineList: EditorLine[];
}

export type EditorAnswerMap = Map<number, EditorAnswer>;

/**
 * 구문에 대한 정보
 */
export interface EditorLine {
  lineIndex: number; // 구문 index
  text: string; // 구문 글자
  highlightList: HighlightRange[]; // 하이라이트 정보, 하이라이트 정보가 없으면 빈배열
}

export interface Position {
  top: string;
  left: string;
}
