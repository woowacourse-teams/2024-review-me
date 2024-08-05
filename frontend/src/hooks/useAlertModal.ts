import { useState } from 'react';

const useAlertModal = () => {
  const [isAlertModalOpen, setIsAlertModalOpen] = useState(false);

  const openAlertModal = () => setIsAlertModalOpen(true);
  const closeAlertModal = () => setIsAlertModalOpen(false);

  return {
    isAlertModalOpen,
    openAlertModal,
    closeAlertModal,
  };
};

export default useAlertModal;
