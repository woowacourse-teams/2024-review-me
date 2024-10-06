export interface Highlight {
  start: number;
  end: number;
}

export interface EditorBlockData {
  text: string;
  highlightList: Highlight[];
}
