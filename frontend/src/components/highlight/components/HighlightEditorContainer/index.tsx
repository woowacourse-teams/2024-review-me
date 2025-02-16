import { useEffect } from 'react';

import { LOCAL_STORAGE_KEY } from '@/constants';

import { ErrorBoundary } from '../../../error';
import ErrorFallback from '../../../error/ErrorFallback';
import HighlightEditor, { HighlightEditorProps } from '../HighlightEditor';

interface HighlightEditorContainerProps extends Omit<HighlightEditorProps, 'handleErrorModal' | 'handleModalMessage'> {}

const HighlightEditorContainer = (props: HighlightEditorContainerProps) => {
  useEffect(() => {
    return () => {
      // NOTE: API 오류 시, HighlightEditor가 재렌더링되어서, LOCAL_STORAGE_KEY.isHighlightEditable 삭제되는 것을 막기 위해 HighlightEditorContainer 언마운트 시 삭제해야함
      localStorage.removeItem(LOCAL_STORAGE_KEY.isHighlightEditable);
    };
  }, []);
  return (
    <>
      <ErrorBoundary fallback={ErrorFallback}>
        <HighlightEditor {...props} />
      </ErrorBoundary>
    </>
  );
};

export default HighlightEditorContainer;
