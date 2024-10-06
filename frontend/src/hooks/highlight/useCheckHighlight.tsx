import { useState } from 'react';

import { HIGHLIGHT_SPAN_CLASS_NAME } from '@/constants';
import { isExistentElement, SelectionInfo } from '@/utils';

const useCheckHighlight = () => {
  const [isAddingHighlight, setIsAddingHighlight] = useState(false);

  const checkHighlight = (info: SelectionInfo) => {
    const { selection } = info;
    const anchorSpan = selection.anchorNode?.parentElement;
    const focusSpan = selection.focusNode?.parentElement;

    if (!anchorSpan) return isExistentElement(anchorSpan, 'anchorNode의 부모인span');
    if (!focusSpan) return isExistentElement(focusSpan, 'anchorNode의 부모인span');

    const isAnchorHighlight = anchorSpan.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME);
    const isFocusHighlight = focusSpan.classList.contains(HIGHLIGHT_SPAN_CLASS_NAME);

    const isHighlight = isAnchorHighlight || isFocusHighlight;
    setIsAddingHighlight(!isHighlight);
  };

  return {
    isAddingHighlight,
    checkHighlight,
  };
};

export default useCheckHighlight;
