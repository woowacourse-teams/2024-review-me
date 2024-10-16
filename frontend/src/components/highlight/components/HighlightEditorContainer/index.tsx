import { useState } from 'react';

import { ErrorBoundary } from '../../../error';
import ErrorFallback from '../../../error/ErrorFallback';
import HighlightEditor, { HighlightEditorProps } from '../HighlightEditor';

const HighlightEditorContainer = (props: Omit<HighlightEditorProps, 'handleErrorModal'>) => {
  const [isOpenErrorModal, setIsOpenErrorModal] = useState(false);
  const handleErrorModal = (isError: boolean) => setIsOpenErrorModal(isError);
  return (
    <>
      <ErrorBoundary fallback={ErrorFallback}>
        <HighlightEditor {...props} handleErrorModal={handleErrorModal} />
      </ErrorBoundary>
      {isOpenErrorModal && <div>오류</div>}
    </>
  );
};

export default HighlightEditorContainer;
