import { useState, useEffect } from 'react';

const TOP_BUTTON_DISPLAY_THRESHOLD = 500;

interface UseTopButtonResult {
  showTopButton: boolean;
  scrollToTop: () => void;
}

const useTopButton = (): UseTopButtonResult => {
  const [showTopButton, setShowTopButton] = useState(false);

  useEffect(() => {
    const handleShowTopButton = () => {
      if (window.scrollY > TOP_BUTTON_DISPLAY_THRESHOLD) {
        setShowTopButton(true);
      } else {
        setShowTopButton(false);
      }
    };

    window.addEventListener('scroll', handleShowTopButton);

    return () => {
      window.removeEventListener('scroll', handleShowTopButton);
    };
  }, []);

  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  return { showTopButton, scrollToTop };
};

export default useTopButton;
