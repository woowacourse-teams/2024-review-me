import { useLocation } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { ROUTE } from '@/constants';

import Logo from './components/Logo';
import * as S from './styles';

const Topbar = () => {
  // TODO: 임시로 true 설정 (로그인 기능 추가하면서 여기도 수정해야 한다.)
  const isUserLoggedIn = true;
  const { pathname } = useLocation();

  const isShowNavigationTab = isUserLoggedIn && !['/', ROUTE.reviewZone].includes(pathname);

  return (
    <S.Layout $hideBorder={isShowNavigationTab}>
      <S.Container>
        <UndraggableWrapper>
          <Logo />
        </UndraggableWrapper>
      </S.Container>
    </S.Layout>
  );
};

export default Topbar;
