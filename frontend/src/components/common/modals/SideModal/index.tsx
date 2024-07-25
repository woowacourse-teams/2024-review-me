import React, { PropsWithChildren } from 'react';

import ModalPortal from '@/components/common/modals/ModalPortal';

import * as S from './styles';

interface SideModalProps {
  isSidebarHidden: boolean;
}

const SideModal: React.FC<PropsWithChildren<SideModalProps>> = ({ children: Sidebar, isSidebarHidden }) => {
  return (
    <ModalPortal id="sidebarModal-portal">
      <S.SidebarBackground>
        <S.SidebarWrapper $isSidebarHidden={isSidebarHidden}>{Sidebar}</S.SidebarWrapper>
      </S.SidebarBackground>
    </ModalPortal>
  );
};

export default SideModal;
