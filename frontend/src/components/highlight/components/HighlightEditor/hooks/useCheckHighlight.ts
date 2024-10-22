import { useState } from 'react';

import { HIGHLIGHT_SPAN_CLASS_NAME, SYNTAX_BASIC_CLASS_NAME } from '@/constants';
import { SelectionInfo } from '@/utils';

export type HighlightArea = 'full' | 'partial' | 'none';

const useCheckHighlight = () => {
  const [highlightArea, setHighlightArea] = useState<HighlightArea>('none');

  const checkHighlight = (info: SelectionInfo) => {
    const selectedAllSpanList = getAllSpanInSelection(info.selection);
    let highlightedSpanLength = 0;

    selectedAllSpanList.forEach((span) => {
      if (span.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME)) highlightedSpanLength += 1;
    });

    const newHighlightArea: HighlightArea = highlightedSpanLength
      ? selectedAllSpanList.length === highlightedSpanLength
        ? 'full'
        : 'partial'
      : 'none';

    setHighlightArea(newHighlightArea);

    return newHighlightArea;
  };

  const getAllSpanInSelection = (selection: Selection) => {
    const range = selection.getRangeAt(0);
    const sentenceElList = document.getElementsByClassName(SYNTAX_BASIC_CLASS_NAME);

    return [...sentenceElList].filter((el) => range.intersectsNode(el));
  };

  return {
    highlightArea,
    checkHighlight,
  };
};

export default useCheckHighlight;
