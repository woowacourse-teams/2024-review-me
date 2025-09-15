import { useState } from 'react';

export type Modals = Record<string, boolean>;

interface UseModalsProps {
  initialStates?: Modals;
}

const useModals = ({ initialStates }: UseModalsProps = {}) => {
  const [modals, setModals] = useState<Modals>(initialStates ?? {});

  const openModal = (key: string) => {
    setModals((prev) => ({
      ...prev,
      [key]: true,
    }));
  };

  const closeModal = (key: string) => {
    setModals((prev) => ({
      ...prev,
      [key]: false,
    }));
  };

  const isOpen = (key: string) => !!modals[key];

  return { isOpen, openModal, closeModal };
};

export default useModals;
