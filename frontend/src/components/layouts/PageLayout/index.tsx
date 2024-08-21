import { PropsWithChildren } from 'react';

import { TopButton } from '@/components/common';

import * as S from './styles';

const PageLayout = ({ children }: PropsWithChildren) => {
  return (
    <S.Layout>
      <S.Wrapper>{children}</S.Wrapper>
      <TopButton />
    </S.Layout>
  );
};

export default PageLayout;
