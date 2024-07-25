import React, { PropsWithChildren } from 'react';

import * as S from './styles';

function ModalBackground({ children }: PropsWithChildren) {
  return <S.ModalBackground>{children}</S.ModalBackground>;
}

export default ModalBackground;
