import React, { PropsWithChildren, useRef } from 'react';

import { useModalClose } from '@/hooks';

import * as S from './styles';

interface ModalBackgroundProps {
  closeModal: () => void;
}

const ModalBackground: React.FC<PropsWithChildren<ModalBackgroundProps>> = ({ children, closeModal }) => {
  const modalRef = useRef<HTMLDivElement>(null);
  useModalClose(closeModal, modalRef);

  return (
    <S.ModalBackground>
      <div ref={modalRef}>{children}</div>
    </S.ModalBackground>
  );
};

export default ModalBackground;
