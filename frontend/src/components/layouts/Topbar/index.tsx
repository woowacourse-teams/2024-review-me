import { useLocation } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';
import { ROUTE } from '@/constants';

import Logo from './components/Logo';
import * as S from './styles';

const Topbar = () => {
  const { pathname } = useLocation();
  const $hasNavigationTab = [ROUTE.reviewLinks, ROUTE.writtenReview].includes(pathname);

  return (
    <S.Layout $hasNavigationTab={$hasNavigationTab}>
      <S.Container>
        <UndraggableWrapper>
          <Logo />
        </UndraggableWrapper>
      </S.Container>
    </S.Layout>
  );
};

export default Topbar;
