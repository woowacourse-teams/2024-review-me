import { PropsWithChildren } from 'react';

import * as S from './styles';

const PageLayout = ({ children }: PropsWithChildren) => {
  return (
    <S.Layout>
      <S.Wrapper>{children}</S.Wrapper>
    </S.Layout>
  );
};

export default PageLayout;
