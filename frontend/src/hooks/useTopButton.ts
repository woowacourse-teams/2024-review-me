import { useState, useEffect } from 'react';

const TOP_BUTTON_DISPLAY_THRESHOLD = 500;

const useTopButton = () => {
  const [showTopButton, setShowTopButton] = useState(false);

  const handleShowTopButton = () => {
    if (window.scrollY > TOP_BUTTON_DISPLAY_THRESHOLD) {
      setShowTopButton(true);
    } else {
      setShowTopButton(false);
    }
  };

  useEffect(() => {
    window.addEventListener('scroll', handleShowTopButton);

    return () => {
      window.removeEventListener('scroll', handleShowTopButton);
    };
  }, []);

  return { showTopButton };
};

export default useTopButton;
