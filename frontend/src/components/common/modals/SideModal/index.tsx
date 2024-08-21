import React, { PropsWithChildren } from 'react';

import ModalPortal from '@/components/common/modals/ModalPortal';

import ModalBackground from '../ModalBackground';

import * as S from './styles';

interface SideModalProps {
  isSidebarHidden: boolean;
  closeModal: () => void;
}

const SideModal: React.FC<PropsWithChildren<SideModalProps>> = ({ children: Sidebar, isSidebarHidden, closeModal }) => {
  return (
    <ModalPortal id="sidebarModal-portal">
      <ModalBackground closeModal={closeModal}>
        <S.SidebarWrapper $isSidebarHidden={isSidebarHidden}>{Sidebar}</S.SidebarWrapper>
      </ModalBackground>
    </ModalPortal>
  );
};

export default SideModal;
