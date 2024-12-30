import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface WrittenReviewItemProps {
  title: string;
}

const WrittenReviewItem = ({ title, children }: EssentialPropsWithChildren<WrittenReviewItemProps>) => {
  return (
    <S.WrittenReviewItem>
      <S.Title>{title}</S.Title>
      <S.Content>{children}</S.Content>
    </S.WrittenReviewItem>
  );
};

export default WrittenReviewItem;
