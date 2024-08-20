import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface MainProps {
  isBreadCrumb: boolean;
}

const Main = ({ children, isBreadCrumb }: EssentialPropsWithChildren<MainProps>) => {
  return (
    <S.MainContainer $isBreadCrumb={isBreadCrumb}>
      <S.Contents>{children}</S.Contents>
    </S.MainContainer>
  );
};

export default Main;
