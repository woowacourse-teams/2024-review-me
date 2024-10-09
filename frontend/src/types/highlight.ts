export interface Highlight {
  startIndex: number;
  endIndex: number;
}
}

export interface EditorBlockData {
  text: string;
  highlightList: Highlight[];
}

export interface Position {
  top: number;
  left: number;
}
