import { useState } from 'react';

import { ErrorSuspenseContainer } from '../error';

import HighlightEditor, { HighlightEditorProps } from './HighlightEditor';

const HighlightEditorContainer = (props: Omit<HighlightEditorProps, 'handleErrorModal'>) => {
  const [openErrorModal, setErrorModal] = useState(false);
  const handleErrorModal = (isError: boolean) => setErrorModal(isError);
  return (
    <>
      <ErrorSuspenseContainer>
        <HighlightEditor {...props} handleErrorModal={handleErrorModal} />
      </ErrorSuspenseContainer>
      {openErrorModal && <div>오류</div>}
    </>
  );
};

export default HighlightEditorContainer;
