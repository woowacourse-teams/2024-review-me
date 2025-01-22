import React from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from './style';

const BlinkLoader = ({ children }: EssentialPropsWithChildren) => {
  return <S.Loader>{children}</S.Loader>;
};

export default BlinkLoader;
