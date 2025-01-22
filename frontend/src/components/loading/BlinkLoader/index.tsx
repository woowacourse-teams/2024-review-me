import React from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from './style';

interface BlinkLoaderProps extends EssentialPropsWithChildren, S.LoaderProps {}

const BlinkLoader = ({ children, $animationDurationTime }: BlinkLoaderProps) => {
  return <S.Loader $animationDurationTime={$animationDurationTime}>{children}</S.Loader>;
};

export default BlinkLoader;
