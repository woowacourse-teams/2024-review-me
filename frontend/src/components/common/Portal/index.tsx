import { PropsWithChildren, useEffect } from 'react';
import { createPortal } from 'react-dom';

import * as S from './styles';

interface PortalProps {
  id?: string;
  disableScroll?: boolean;
}

const Portal: React.FC<PropsWithChildren<PortalProps>> = ({ children: Modal, id, disableScroll = true }) => {
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

  return createPortal(<S.Portal id={id || 'portal'}>{Modal}</S.Portal>, document.body);
};

export default Portal;
