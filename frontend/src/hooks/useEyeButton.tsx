import { useState } from 'react';

const useEyeButton = () => {
  const [isOff, setIsOff] = useState(true);

  const handleEyeButtonToggle = () => {
    setIsOff(!isOff);
  };

  return {
    isOff,
    handleEyeButtonToggle,
  };
};

export default useEyeButton;
