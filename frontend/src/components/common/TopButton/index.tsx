import { RefObject } from 'react';

import UpperArrowIcon from '@/assets/upperArrow.svg';
import useTopButton from '@/hooks/useTopButton';
import { scrollToTop } from '@/utils';

import * as S from './style';

interface TopButtonProps {
  containerRef?: RefObject<HTMLElement>;
}

const TopButton = ({ containerRef }: TopButtonProps) => {
  const { showTopButton } = useTopButton(containerRef);

  if (!showTopButton) return null;

  return (
    <S.TopButton onClick={() => scrollToTop(containerRef)} type="button">
      <S.ArrowImage src={UpperArrowIcon} alt="위 화살표"></S.ArrowImage>
    </S.TopButton>
  );
};

export default TopButton;
