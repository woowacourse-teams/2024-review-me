import { useState } from 'react';

import { HIGHLIGHT_SPAN_CLASS_NAME, SYNTAX_BASIC_CLASS_NAME } from '@/constants';
import { SelectionInfo } from '@/utils';

const useCheckHighlight = () => {
  const [isAddingHighlight, setIsAddingHighlight] = useState(false);

  const checkHighlight = (info: SelectionInfo) => {
    const selectedAllSpanList = getAllSpanInSelection(info.selection);
    const isNoneHighlight = selectedAllSpanList.some((span) => !span.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME));

    setIsAddingHighlight(isNoneHighlight);

    return isNoneHighlight;
  };

  const getAllSpanInSelection = (selection: Selection) => {
    const range = selection.getRangeAt(0);
    const sentenceElList = document.getElementsByClassName(SYNTAX_BASIC_CLASS_NAME);

    return [...sentenceElList].filter((el) => range.intersectsNode(el));
  };

  return {
    isAddingHighlight,
    checkHighlight,
  };
};

export default useCheckHighlight;
