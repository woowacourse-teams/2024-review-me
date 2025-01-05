import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface WrittenReviewItemProps {
  title: string;
}

const PageContentLayout = ({ title, children }: EssentialPropsWithChildren<WrittenReviewItemProps>) => {
  return (
    <S.PageContentLayout>
      <S.Title>{title}</S.Title>
      <S.Content>{children}</S.Content>
    </S.PageContentLayout>
  );
};

export default PageContentLayout;
