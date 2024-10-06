export interface Highlight {
  start: number;
  length: number;
}

export interface EditorBlockData {
  text: string;
  highlightList: Highlight[];
}
