import { useState, useEffect, RefObject } from 'react';

const TOP_BUTTON_DISPLAY_THRESHOLD = 500;

const useTopButton = (containerRef?: RefObject<HTMLElement>) => {
  const [showTopButton, setShowTopButton] = useState(false);

  useEffect(() => {
    const handleShowTopButton = () => {
      const containerElement = containerRef?.current;
      const currentScrollTop = containerElement ? containerElement.scrollTop : window.scrollY;
      setShowTopButton(currentScrollTop > TOP_BUTTON_DISPLAY_THRESHOLD);
    };

    const containerElement = containerRef?.current || window;
    containerElement.addEventListener('scroll', handleShowTopButton);

    return () => {
      containerElement.removeEventListener('scroll', handleShowTopButton);
    };
  }, [containerRef?.current]);

  return { showTopButton };
};

export default useTopButton;
