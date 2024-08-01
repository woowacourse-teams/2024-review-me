import React, { PropsWithChildren, useRef } from 'react';

import { useModalClose } from '@/hooks';

import * as S from './styles';

interface ModalBackgroundProps {
  closeModalOnBackground: () => void;
  closeModalOnEsc: () => void;
}

const ModalBackground: React.FC<PropsWithChildren<ModalBackgroundProps>> = ({
  children,
  closeModalOnBackground,
  closeModalOnEsc,
}) => {
  const modalBackgroundRef = useRef<HTMLDivElement>(null);
  useModalClose({ closeModalOnBackground, closeModalOnEsc, modalBackgroundRef });

  return <S.ModalBackground ref={modalBackgroundRef}>{children}</S.ModalBackground>;
};

export default ModalBackground;
