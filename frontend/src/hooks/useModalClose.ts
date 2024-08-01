import { useEffect, RefObject } from 'react';

interface UseModalCloseProps {
  closeModal: (() => void) | null;
  modalBackgroundRef: RefObject<HTMLElement>;
}

const useModalClose = ({ closeModal, modalBackgroundRef }: UseModalCloseProps) => {
  const isNodeElement = (element: EventTarget | null): element is Node => {
    return element instanceof Node;
  };

  const isModalBackground = (targetElement: Node | null) => {
    return modalBackgroundRef.current ? modalBackgroundRef.current === targetElement : false;
  };

  const isHTMLElement = (element: Element | null): element is HTMLElement => {
    return element instanceof HTMLElement;
  };

  // NOTE: esc 키를 눌렀을 때 햄버거 버튼이 포커싱되는 문제 해결을 위한 함수
  const blurFocusing = () => {
    const activeElement = document.activeElement;

    if (!isHTMLElement(activeElement)) return;
    if (typeof activeElement.blur === 'function') activeElement.blur();
  };

  const handleBackgroundClick = (event: MouseEvent) => {
    if (isNodeElement(event.target) && isModalBackground(event.target)) {
      if (!closeModal) return;
      closeModal();
    }
  };

  const handleKeyDown = (event: KeyboardEvent) => {
    if (event.key === 'Escape') {
      event.preventDefault();

      blurFocusing();

      if (!closeModal) return;
      closeModal();
    }
  };

  useEffect(() => {
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
