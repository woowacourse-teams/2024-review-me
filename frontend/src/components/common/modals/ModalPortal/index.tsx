import { PropsWithChildren, useEffect } from 'react';
import { createPortal } from 'react-dom';

import * as S from './styles';

interface ModalPortalProps {
  id?: string;
}

const ModalPortal: React.FC<PropsWithChildren<ModalPortalProps>> = ({ children: Modal, id }) => {
  const preventBodyScroll = () => {
    document.body.style.overflow = 'hidden';
  };

  const allowBodyScroll = () => {
    document.body.style.overflow = '';
  };

  useEffect(() => {
    preventBodyScroll();

    return () => {
      allowBodyScroll();
    };
  });

  return createPortal(<S.ModalPortal id={id || 'modal-portal'}>{Modal}</S.ModalPortal>, document.body);
};

export default ModalPortal;
