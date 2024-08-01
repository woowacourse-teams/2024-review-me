import React from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from '../../styles';
import { FormDirection } from '../FormLayout';

interface FormBodyProps {
  direction: FormDirection;
}

const FormBody: React.FC<EssentialPropsWithChildren<FormBodyProps>> = ({ direction, children }) => {
  return <div css={S.FormBody(direction)}>{children}</div>;
};

export default FormBody;
