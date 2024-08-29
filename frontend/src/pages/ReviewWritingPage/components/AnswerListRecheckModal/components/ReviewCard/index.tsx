import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface ReviewCardProps {
  title: string;
}

const ReviewCard = ({ title, children }: EssentialPropsWithChildren<ReviewCardProps>) => {
  return (
    <S.Container>
      <S.Header>
        <S.Title>{title}</S.Title>
      </S.Header>
      <S.Main>{children}</S.Main>
    </S.Container>
  );
};

export default ReviewCard;
