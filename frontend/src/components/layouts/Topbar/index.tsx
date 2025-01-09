import { useLocation } from 'react-router';

import UndraggableWrapper from '@/components/common/UndraggableWrapper';

import Logo from './components/Logo';
import * as S from './styles';

const Topbar = () => {
  const { pathname } = useLocation();
  // TODO: '리뷰 링크 확인', '작성한 리뷰 확인' 페이지 URL 경로 확정되면 변경
  const $hasNavigationTab = ['user/review-link-management', 'user/written-review-confirm'].includes(pathname);

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
