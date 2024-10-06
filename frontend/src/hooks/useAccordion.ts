import { useState } from 'react';

const useAccordion = () => {
  const [isOpened, setIsOpened] = useState(false);

  const handleAccordionButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  return {
    isOpened,
    handleAccordionButtonClick,
  };
};

export default useAccordion;
