import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface MainProps {
  isShowBreadCrumb?: boolean;
  isShowNavigationTab?: boolean;
}

const Main = ({ children, isShowBreadCrumb, isShowNavigationTab }: EssentialPropsWithChildren<MainProps>) => {
  return (
    <S.MainContainer $isShowBreadCrumb={isShowBreadCrumb} $isShowNavigationTab={isShowNavigationTab}>
      <S.Contents>{children}</S.Contents>
    </S.MainContainer>
  );
};

export default Main;
