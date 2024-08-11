import { useEffect, useRef, useState } from 'react';

const useSlideWidth = () => {
  const INITIAL_SIDE_WIDTH = 0;
  const [slideWidth, setSlideWidth] = useState(INITIAL_SIDE_WIDTH);
  const wrapperRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (wrapperRef.current) setSlideWidth(wrapperRef.current.clientWidth);
  }, [wrapperRef]);

  return {
    wrapperRef,
    slideWidth,
  };
};

export default useSlideWidth;
