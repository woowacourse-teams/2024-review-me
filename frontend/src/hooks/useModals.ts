import { useState } from 'react';

interface Modals {
  [key: string]: boolean;
}

const useModals = () => {
  const [modals, setModals] = useState<Modals>({});

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

  const isOpen = (key: string) => modals[key];

  return { isOpen, openModal, closeModal };
};

export default useModals;
