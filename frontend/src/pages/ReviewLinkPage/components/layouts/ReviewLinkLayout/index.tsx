import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface ReviewLinkListLayoutProps {
  title: string;
  subTitle: string;
}

const ReviewLinkLayout = ({ title, subTitle, children }: EssentialPropsWithChildren<ReviewLinkListLayoutProps>) => {
  return (
    <S.ReviewLinkLayout>
      <S.TitleWrapper>
        <S.Title>{title}</S.Title>
        <S.SubTitle>{subTitle}</S.SubTitle>
      </S.TitleWrapper>
      <S.CardList>{children}</S.CardList>
    </S.ReviewLinkLayout>
  );
};

export default ReviewLinkLayout;
