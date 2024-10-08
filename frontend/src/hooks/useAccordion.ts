import { useState } from 'react';

interface UseAccordionProps {
  initiallyOpened: boolean;
}

const useAccordion = ({ initiallyOpened }: UseAccordionProps) => {
  const [isOpened, setIsOpened] = useState(initiallyOpened);

  const handleAccordionButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  return {
    isOpened,
    handleAccordionButtonClick,
  };
};

export default useAccordion;
