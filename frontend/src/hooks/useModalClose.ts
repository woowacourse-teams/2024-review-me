import { useEffect, RefObject } from 'react';

const useModalClose = (closeModal: () => void, modalBackgroundRef: RefObject<HTMLElement>) => {
  const isNodeElement = (element: EventTarget | null): element is Node => {
    return element instanceof Node;
  };

  const isModalBackground = (targetElement: Node | null) => {
    return modalBackgroundRef.current ? modalBackgroundRef.current === targetElement : false;
  };

  const isHTMLElement = (element: Element | null): element is HTMLElement => {
    return element instanceof HTMLElement;
  };

  const blurFocusing = () => {
    const activeElement = document.activeElement;

    if (!isHTMLElement(activeElement)) return;
    if (typeof activeElement.blur === 'function') activeElement.blur();
  };

  useEffect(() => {
    const handleBackgroundClick = (event: MouseEvent) => {
      if (isNodeElement(event.target) && isModalBackground(event.target)) {
        closeModal();
      }
    };

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') {
        event.preventDefault();
        blurFocusing();
        closeModal();
      }
    };

    const modalBackgroundElement = modalBackgroundRef.current;

    modalBackgroundElement?.addEventListener('click', handleBackgroundClick);
    document.addEventListener('keydown', handleKeyDown);

    return () => {
      modalBackgroundElement?.removeEventListener('click', handleBackgroundClick);
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [closeModal, modalBackgroundRef]);
};

export default useModalClose;
