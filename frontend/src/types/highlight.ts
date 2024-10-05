export interface Highlight {
  start: number;
  length: number;
}

export interface EditorBlock {
  text: string;
  highlightList: Highlight[];
}
