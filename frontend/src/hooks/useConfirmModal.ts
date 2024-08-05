import { useState } from 'react';

const useConfirmModal = () => {
  const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);

  const openConfirmModal = () => setIsConfirmModalOpen(true);
  const closeConfirmModal = () => setIsConfirmModalOpen(false);

  return {
    isConfirmModalOpen,
    openConfirmModal,
    closeConfirmModal,
  };
};

export default useConfirmModal;
