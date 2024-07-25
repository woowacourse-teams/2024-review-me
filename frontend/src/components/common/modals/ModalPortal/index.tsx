import { PropsWithChildren, useEffect } from 'react';
import { createPortal } from 'react-dom';

import * as S from './styles';

interface ModalPortalProps {
  id?: string;
}

const ModalPortal: React.FC<PropsWithChildren<ModalPortalProps>> = ({ children: Modal, id }) => {
  const stopModalPropagation = (e: globalThis.MouseEvent) => {
    e.stopPropagation();
  };

  useEffect(() => {
    document.body.addEventListener('click', stopModalPropagation);

    return () => {
      document.body.removeEventListener('click', stopModalPropagation);
    };
  }, []);

  return createPortal(<S.ModalPortal id={id || 'modal-portal'}>{Modal}</S.ModalPortal>, document.body);
};

export default ModalPortal;
