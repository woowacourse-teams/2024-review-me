import { useEffect, useState } from 'react';

import { SESSION_STORAGE_KEY } from '@/constants';

const useEditableState = () => {
  const [isEditable, setIsEditable] = useState(false);

  const getHighlightEditorStateInStorage = () => sessionStorage.getItem(SESSION_STORAGE_KEY.isHighlightEditable);

  const saveHighlightEditorStateInStorage = () => {
    sessionStorage.setItem(SESSION_STORAGE_KEY.isHighlightEditable, 'true');
  };

  const removeHighlightEditorStateFromStorage = () => {
    sessionStorage.removeItem(SESSION_STORAGE_KEY.isHighlightEditable);
  };

  const handleEditToggleButton = () => {
    setIsEditable((prev) => {
      prev ? removeHighlightEditorStateFromStorage() : saveHighlightEditorStateInStorage();
      return !prev;
    });
  };

  useEffect(() => {
    const storageItem = getHighlightEditorStateInStorage();
    if (storageItem) setIsEditable(true);

    return () => {
      removeHighlightEditorStateFromStorage();
    };
  }, []);

  return {
    isEditable,
    handleEditToggleButton,
  };
};

export default useEditableState;
