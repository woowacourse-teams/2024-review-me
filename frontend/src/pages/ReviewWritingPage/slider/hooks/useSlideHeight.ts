import { useLayoutEffect, useRef, useState } from 'react';

interface UseSlideHeightProps {
  currentCardIndex: number;
}

const SLIDE_CLASS_NAME = 'reviewWritingCardSlide';
const REM_UNIT = 10;

const useSlideHeight = ({ currentCardIndex }: UseSlideHeightProps) => {
  // TODO: slideWidth 필요없어짐 추후 삭제
  const [slideHeight, setSlideHeight] = useState<string>('auto');
  const wrapperRef = useRef<HTMLDivElement | null>(null);
  const makeId = (index: number) => `${SLIDE_CLASS_NAME}_${index}`;
  const targetSlide = document.getElementById(makeId(currentCardIndex));

  useLayoutEffect(() => {
    if (targetSlide) {
      const height = Number(targetSlide.offsetHeight) / REM_UNIT;
      setSlideHeight(`${height}rem`);
    }
  }, [targetSlide]);

  return {
    wrapperRef,
    slideHeight,
    makeId,
  };
};

export default useSlideHeight;
