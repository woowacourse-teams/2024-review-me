import { useEffect, RefObject } from 'react';

const useModalClose = (closeModal: () => void, modalRef: RefObject<HTMLElement>) => {
  const isNodeElement = (element: EventTarget | null): element is Node => {
    return element instanceof Node;
  };

  const isModalChildren = (targetElement: Node | null) => {
    return modalRef.current ? modalRef.current.contains(targetElement) : false;
  };

  useEffect(() => {
    const handleBackgroundClick = (event: MouseEvent) => {
      if (isNodeElement(event.target) && !isModalChildren(event.target)) {
        closeModal();
      }
    };

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') closeModal();
    };

    // NOTE: 모달을 여는 클릭 이벤트가 모달을 닫는 핸들러의 이벤트로 들어가는 것을 막기 위해
    // 모달을 닫는 클릭 이벤트에 대한 핸들러를 capture 단계에서 추가
    document.addEventListener('click', handleBackgroundClick, true);
    document.addEventListener('keydown', handleKeyDown);

    return () => {
      document.removeEventListener('click', handleBackgroundClick, true);
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [closeModal, modalRef]);
};

export default useModalClose;
