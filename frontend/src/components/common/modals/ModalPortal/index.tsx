import { PropsWithChildren, useEffect } from 'react';
import { createPortal } from 'react-dom';

import * as S from './styles';

interface ModalPortalProps {
  id?: string;
  disableScroll?: boolean;
}

const ModalPortal: React.FC<PropsWithChildren<ModalPortalProps>> = ({ children: Modal, id, disableScroll = true }) => {
  const preventBodyScroll = () => {
    document.body.style.overflow = 'hidden';
  };

  const allowBodyScroll = () => {
    document.body.style.overflow = '';
  };

  useEffect(() => {
    if (disableScroll) preventBodyScroll();

    return () => {
      if (disableScroll) allowBodyScroll();
    };
  });

  return createPortal(<S.ModalPortal id={id || 'modal-portal'}>{Modal}</S.ModalPortal>, document.body);
};

export default ModalPortal;
