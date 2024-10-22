import { useState } from 'react';

import WarningIcon from '@/assets/warning.svg';
import Toast from '@/components/common/Toast';

import { ErrorBoundary } from '../../../error';
import ErrorFallback from '../../../error/ErrorFallback';
import HighlightEditor, { HighlightEditorProps } from '../HighlightEditor';

const HighlightEditorContainer = (props: Omit<HighlightEditorProps, 'handleErrorModal'>) => {
  const [isOpenErrorModal, setIsOpenErrorModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');

  const handleErrorModal = (isError: boolean) => setIsOpenErrorModal(isError);
  const handleModalMessage = (message: string) => setModalMessage(message);

  return (
    <>
      <ErrorBoundary fallback={ErrorFallback}>
        <HighlightEditor {...props} handleErrorModal={handleErrorModal} handleModalMessage={handleModalMessage} />
      </ErrorBoundary>
      {isOpenErrorModal && (
        <Toast
          icon={{ src: WarningIcon, alt: '경고 아이콘' }}
          message={modalMessage}
          duration={5}
          position="bottom"
          handleOpenModal={handleErrorModal}
          handleModalMessage={handleModalMessage}
        />
      )}
    </>
  );
};

export default HighlightEditorContainer;
