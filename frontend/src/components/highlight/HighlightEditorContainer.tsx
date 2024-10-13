import { useState } from 'react';

import { ErrorBoundary } from '../error';
import ErrorFallback from '../error/ErrorFallback';

import HighlightEditor, { HighlightEditorProps } from './HighlightEditor';

const HighlightEditorContainer = (props: Omit<HighlightEditorProps, 'handleErrorModal'>) => {
  const [openErrorModal, setErrorModal] = useState(false);
  const handleErrorModal = (isError: boolean) => setErrorModal(isError);
  return (
    <>
      <ErrorBoundary fallback={ErrorFallback}>
        <HighlightEditor {...props} handleErrorModal={handleErrorModal} />
      </ErrorBoundary>
      {openErrorModal && <div>오류</div>}
    </>
  );
};

export default HighlightEditorContainer;
