import React, { PropsWithChildren, useRef } from 'react';

import { useModalClose } from '@/hooks';

import * as S from './styles';

interface ModalBackgroundProps {
  closeModal: (() => void) | null;
}
// TODO: 배경, ESC를 통한 모달 닫히는 기능을 불리해야함
const ModalBackground: React.FC<PropsWithChildren<ModalBackgroundProps>> = ({ children, closeModal }) => {
  const modalBackgroundRef = useRef<HTMLDivElement>(null);
  useModalClose({ closeModal, modalBackgroundRef });

  return <S.ModalBackground ref={modalBackgroundRef}>{children}</S.ModalBackground>;
};

export default ModalBackground;
