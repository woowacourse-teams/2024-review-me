import React, { PropsWithChildren } from 'react';

import ModalPortal from '@/components/common/modals/ModalPortal';

import ModalBackground from '../ModalBackground';

import * as S from './styles';

interface SideModalProps {
  isSidebarHidden: boolean;
}

const SideModal: React.FC<PropsWithChildren<SideModalProps>> = ({ children: Sidebar, isSidebarHidden }) => {
  return (
    <ModalPortal id="sidebarModal-portal">
      <ModalBackground>
        <S.SidebarWrapper $isSidebarHidden={isSidebarHidden}>{Sidebar}</S.SidebarWrapper>
      </ModalBackground>
    </ModalPortal>
  );
};

export default SideModal;
