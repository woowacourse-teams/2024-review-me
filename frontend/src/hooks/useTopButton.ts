import { useState, useEffect, RefObject } from 'react';

const TOP_BUTTON_DISPLAY_THRESHOLD = 500;

const useTopButton = (containerRef?: RefObject<HTMLElement>) => {
  const [showTopButton, setShowTopButton] = useState(false);

  const containerScrollTop = containerRef?.current?.scrollTop;

  useEffect(() => {
    const handleShowTopButton = () => {
      const currentScroll = containerScrollTop ?? window.scrollY;
      setShowTopButton(currentScroll > TOP_BUTTON_DISPLAY_THRESHOLD);
    };
    handleShowTopButton();

    window.addEventListener('scroll', handleShowTopButton);

    return () => {
      window.removeEventListener('scroll', handleShowTopButton);
    };
  }, [containerScrollTop]);

  return { showTopButton };
};

export default useTopButton;
