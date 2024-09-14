import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface MainProps {
  isShowBreadCrumb?: boolean;
}

const Main = ({ children, isShowBreadCrumb }: EssentialPropsWithChildren<MainProps>) => {
  return (
    <S.MainContainer $isShowBreadCrumb={isShowBreadCrumb}>
      <S.Contents>{children}</S.Contents>
    </S.MainContainer>
  );
};

export default Main;
