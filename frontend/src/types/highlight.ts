export interface Highlight {
  startIndex: number;
  endIndex: number;
}

// 서버에서 내려주고, 받는 데이터 타입
export interface HighlightData {
  lineIndex: number;
  // 서버에서는 ranges로 줌
  rangeList: Highlight[];
}
export interface EditorAnswerData {
  content: string;
  answerId: number;
  //서버에서는 highlights로 줌
  highlightList: HighlightData[];
}

// 클라이언트에서 사용하는 형광펜 대상 주관식 답변 타입
export interface EditorAnswer {
  content: string;
  answerId: number;
  answerIndex: number;
  blockList: EditorBlockData[];
}

export type EditorAnswerMap = Map<number, EditorAnswer>;
export interface EditorBlockData {
  text: string;
  highlightList: Highlight[];
}

export interface Position {
  top: number;
  left: number;
}
