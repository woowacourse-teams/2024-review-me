import React from 'react';

import { EssentialPropsWithChildren, ReviewWritingCardSection } from '@/types';

import * as S from './style';

interface ReviewWritingCardLayoutProps {
  cardSection: ReviewWritingCardSection;
}

const ReviewWritingCardLayout = ({
  cardSection,
  children,
}: EssentialPropsWithChildren<ReviewWritingCardLayoutProps>) => {
  return (
    <S.ReviewWritingCardLayout>
      <S.Header>{cardSection.header}</S.Header>
      <S.Main>{children}</S.Main>
    </S.ReviewWritingCardLayout>
  );
};

export default React.memo(ReviewWritingCardLayout);
