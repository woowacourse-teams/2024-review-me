import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

const PreventDrag = ({ children }: EssentialPropsWithChildren) => {
  return <S.PreventDrag>{children}</S.PreventDrag>;
};

export default PreventDrag;
