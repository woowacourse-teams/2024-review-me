import { useModals } from '@/hooks';
import { Modals } from '@/hooks/modal/useModals';

import { CARD_FORM_MODAL_KEY } from '../../constants';

interface useCardFormModalProps {
  initialStates: Modals;
}

const useCardFormModal = ({ initialStates }: useCardFormModalProps) => {
  const { isOpen, openModal, closeModal } = useModals({ initialStates });

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
