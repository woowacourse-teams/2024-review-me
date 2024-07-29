import { useEffect, RefObject } from 'react';

const useModalClose = (closeModal: () => void, modalBackgroundRef: RefObject<HTMLElement>) => {
  const isNodeElement = (element: EventTarget | null): element is Node => {
    return element instanceof Node;
  };

  const isModalBackground = (targetElement: Node | null) => {
    return modalBackgroundRef.current ? modalBackgroundRef.current === targetElement : false;
  };

  useEffect(() => {
    const handleBackgroundClick = (event: MouseEvent) => {
      if (isNodeElement(event.target) && isModalBackground(event.target)) {
        closeModal();
      }
    };

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') closeModal();
    };

    const modalBackGroundElement = modalBackgroundRef.current;

    modalBackGroundElement?.addEventListener('click', handleBackgroundClick);
    document.addEventListener('keydown', handleKeyDown);

    return () => {
      modalBackGroundElement?.removeEventListener('click', handleBackgroundClick);
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [closeModal, modalBackgroundRef]);
};

export default useModalClose;
