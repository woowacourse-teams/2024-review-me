import { useState } from 'react';

interface UseAccordionProps {
  isInitiallyOpened: boolean;
}

const useAccordion = ({ isInitiallyOpened }: UseAccordionProps) => {
  const [isOpened, setIsOpened] = useState(isInitiallyOpened);

  const handleAccordionButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  return {
    isOpened,
    handleAccordionButtonClick,
  };
};

export default useAccordion;
