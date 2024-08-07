import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface ReviewWritingCardProps {
  title: string;
}

const ReviewWritingCard = ({ title, children }: EssentialPropsWithChildren<ReviewWritingCardProps>) => {
  return (
    <S.Container>
      <S.Title>{title}</S.Title>
      {children}
    </S.Container>
  );
};

export default ReviewWritingCard;
