import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

const UndraggableWrapper = ({ children }: EssentialPropsWithChildren) => {
  return <S.Wrapper>{children}</S.Wrapper>;
};

export default UndraggableWrapper;
