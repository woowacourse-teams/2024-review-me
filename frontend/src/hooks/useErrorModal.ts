import { useState } from 'react';

const useErrorModal = () => {
  const [isErrorModalOpen, setIsErrorModalOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const openErrorModal = (errorMessage: string) => {
    setErrorMessage(errorMessage);
    setIsErrorModalOpen(true);
  };
  const closeErrorModal = () => setIsErrorModalOpen(false);

  return {
    isErrorModalOpen,
    errorMessage,
    openErrorModal,
    closeErrorModal,
  };
};

export default useErrorModal;
