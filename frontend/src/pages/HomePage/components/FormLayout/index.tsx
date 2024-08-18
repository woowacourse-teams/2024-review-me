import React from 'react';

import { EssentialPropsWithChildren } from '@/types';

import { FormBody } from '../index';

import * as S from './styles';

interface FormProps {
  title: string;
  direction: React.CSSProperties['flexDirection'];
}

const FormLayout: React.FC<EssentialPropsWithChildren<FormProps>> = ({ title, direction, children }) => {
  return (
    <S.FormLayout>
      <S.Title>{title}</S.Title>
      <FormBody direction={direction}>{children}</FormBody>
    </S.FormLayout>
  );
};

export default FormLayout;
