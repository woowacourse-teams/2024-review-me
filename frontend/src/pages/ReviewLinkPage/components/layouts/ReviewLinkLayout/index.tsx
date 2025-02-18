import { useEffect, useRef } from 'react';

import { EssentialPropsWithChildren } from '@/types';

import * as S from './styles';

interface ReviewLinkListLayoutProps {
  title: string;
  subTitle: string;
}

const ReviewLinkLayout = ({ title, subTitle, children }: EssentialPropsWithChildren<ReviewLinkListLayoutProps>) => {
  const cardListRef = useRef<HTMLUListElement>(null);

  useEffect(() => {
    const element = cardListRef.current;
    if (!element) return;

    const observer = new ResizeObserver(() => {
      requestAnimationFrame(() => {
        const newPadding = element.scrollHeight > element.clientHeight ? '1rem' : '0';
        if (element.style.paddingRight !== newPadding) {
          element.style.paddingRight = newPadding;
        }
      });
    });

    observer.observe(element);

    return () => observer.disconnect();
  }, []);

  return (
    <S.ReviewLinkLayout>
      <S.TitleWrapper>
        <S.Title>{title}</S.Title>
        <S.SubTitle>{subTitle}</S.SubTitle>
      </S.TitleWrapper>
      <S.CardList ref={cardListRef}>{children}</S.CardList>
    </S.ReviewLinkLayout>
  );
};

export default ReviewLinkLayout;
