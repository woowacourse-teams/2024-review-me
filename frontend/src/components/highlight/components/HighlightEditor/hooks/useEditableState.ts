import { useEffect, useLayoutEffect, useState } from 'react';

import { HIGHLIGHT_EVENT_NAME, LOCAL_STORAGE_KEY } from '@/constants';
import { trackEventInAmplitude } from '@/utils';

const useEditableState = () => {
  const [isEditable, setIsEditable] = useState(false);

  const getHighlightEditorStateInStorage = () => localStorage.getItem(LOCAL_STORAGE_KEY.isHighlightEditable);

  const saveHighlightEditorStateInStorage = () => {
    localStorage.setItem(LOCAL_STORAGE_KEY.isHighlightEditable, 'true');
  };

  const removeHighlightEditorStateFromStorage = () => {
    localStorage.removeItem(LOCAL_STORAGE_KEY.isHighlightEditable);
  };

  const handleEditToggleButton = () => {
    setIsEditable((prev) => {
      if (!prev) trackEventInAmplitude(HIGHLIGHT_EVENT_NAME.openHighlightEditor);

      prev ? removeHighlightEditorStateFromStorage() : saveHighlightEditorStateInStorage();

      return !prev;
    });
  };

  useLayoutEffect(() => {
    const storageItem = getHighlightEditorStateInStorage();
    if (storageItem) {
      setIsEditable(true);
    }
    localStorage.removeItem(LOCAL_STORAGE_KEY.isHighlightError);
  }, []);

  return {
    isEditable,
    handleEditToggleButton,
  };
};

export default useEditableState;
