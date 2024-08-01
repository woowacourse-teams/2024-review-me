import React from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from '../../styles';

interface FormBodyProps {
  direction: React.CSSProperties['flexDirection'];
}

const FormBody: React.FC<EssentialPropsWithChildren<FormBodyProps>> = ({ direction, children }) => {
  return <S.FormBody direction={direction}>{children}</S.FormBody>;
};

export default FormBody;
