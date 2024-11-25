import { useEffect, useState } from 'react';

import WarningIcon from '@/assets/warning.svg';
import Toast from '@/components/common/Toast';
import { LOCAL_STORAGE_KEY } from '@/constants';

import { ErrorBoundary } from '../../../error';
import ErrorFallback from '../../../error/ErrorFallback';
import HighlightEditor, { HighlightEditorProps } from '../HighlightEditor';

interface HighlightEditorContainerProps extends Omit<HighlightEditorProps, 'handleErrorModal' | 'handleModalMessage'> {}

const HighlightEditorContainer = (props: HighlightEditorContainerProps) => {
  const [isOpenErrorModal, setIsOpenErrorModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');

  const handleErrorModal = (isError: boolean) => setIsOpenErrorModal(isError);
  const handleModalMessage = (message: string) => setModalMessage(message);

  useEffect(() => {
    return () => {
      // NOTE: API 오류 시, HighlightEditor가 재렌더링되어서, LOCAL_STORAGE_KEY.isHighlightEditable 삭제되는 것을 막기 위해 HighlightEditorContainer 언마운트 시 삭제해야함
      localStorage.removeItem(LOCAL_STORAGE_KEY.isHighlightEditable);
    };
  }, []);
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
