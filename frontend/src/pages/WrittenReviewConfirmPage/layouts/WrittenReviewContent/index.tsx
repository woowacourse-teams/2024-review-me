import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface WrittenReviewItemProps {
  title: string;
}

const WrittenReviewContent = ({ title, children }: EssentialPropsWithChildren<WrittenReviewItemProps>) => {
  return (
    <S.WrittenReviewContent>
      <S.Title>{title}</S.Title>
      <S.Content>{children}</S.Content>
    </S.WrittenReviewContent>
  );
};

export default WrittenReviewContent;
