import { useModals } from '@/hooks';

import { CARD_FORM_MODAL_KEY } from '../../constants';

const useCardFormModal = () => {
  const { isOpen, openModal, closeModal } = useModals();

  const handleOpenModal = (key: keyof typeof CARD_FORM_MODAL_KEY) => {
    openModal(CARD_FORM_MODAL_KEY[key]);
  };

  return {
    handleOpenModal,
    closeModal,
    isOpen,
  };
};

export default useCardFormModal;
