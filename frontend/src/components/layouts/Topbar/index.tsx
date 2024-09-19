import UndraggableWrapper from '@/components/common/UndraggableWrapper';

import Logo from './components/Logo';
import * as S from './styles';

const Topbar = () => {
  return (
    <S.Layout>
      <S.Container>
        <UndraggableWrapper>
          <Logo />
        </UndraggableWrapper>
      </S.Container>
    </S.Layout>
  );
};

export default Topbar;
