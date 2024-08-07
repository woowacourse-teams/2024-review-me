import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface ReviewWritingCardProps {
  title: string;
}

const ReviewWritingCard = ({ title, children }: EssentialPropsWithChildren<ReviewWritingCardProps>) => {
  return (
    <S.Container>
      <S.Header>
        <S.Title>{title}</S.Title>
      </S.Header>
      <S.Main>{children}</S.Main>
    </S.Container>
  );
};

export default ReviewWritingCard;
