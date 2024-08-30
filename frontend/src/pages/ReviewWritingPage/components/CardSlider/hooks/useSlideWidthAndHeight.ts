import { useLayoutEffect, useRef, useState } from 'react';

interface UseSlideWidthAndHeightProps {
  currentCardIndex: number;
}

const INITIAL_SIDE_WIDTH = 0;
const SLIDE_CLASS_NAME = 'reviewWritingCardSlide';
const REM_UNIT = 10;

const useSlideWidthAndHeight = ({ currentCardIndex }: UseSlideWidthAndHeightProps) => {
  const [slideWidth, setSlideWidth] = useState(INITIAL_SIDE_WIDTH);
  const [slideHeight, setSlideHeight] = useState<string>('auto');
  const wrapperRef = useRef<HTMLDivElement | null>(null);
  const makeId = (index: number) => `${SLIDE_CLASS_NAME}_${index}`;
  const targetSlide = document.getElementById(makeId(currentCardIndex));

  useLayoutEffect(() => {
    if (wrapperRef.current) setSlideWidth(wrapperRef.current.clientWidth);
  }, [wrapperRef]);

  useLayoutEffect(() => {
    if (targetSlide) {
      const height = Number(targetSlide.offsetHeight) / REM_UNIT;
      setSlideHeight(`${height}rem`);
    }
  }, [targetSlide]);

  return {
    wrapperRef,
    slideWidth,
    slideHeight,
    makeId,
  };
};

export default useSlideWidthAndHeight;
