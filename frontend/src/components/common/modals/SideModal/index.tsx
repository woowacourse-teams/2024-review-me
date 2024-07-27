import React, { PropsWithChildren, useRef } from 'react';

import ModalPortal from '@/components/common/modals/ModalPortal';
import { useModalClose } from '@/hooks';

import ModalBackground from '../ModalBackground';

import * as S from './styles';

interface SideModalProps {
  isSidebarHidden: boolean;
  closeModal: () => void;
}

const SideModal: React.FC<PropsWithChildren<SideModalProps>> = ({ children: Sidebar, isSidebarHidden, closeModal }) => {
  const modalRef = useRef<HTMLDivElement>(null);
  useModalClose(closeModal, modalRef);

  return (
    <ModalPortal id="sidebarModal-portal">
      <ModalBackground>
        <S.SidebarWrapper ref={modalRef} $isSidebarHidden={isSidebarHidden}>
          {Sidebar}
        </S.SidebarWrapper>
      </ModalBackground>
    </ModalPortal>
  );
};

export default SideModal;
