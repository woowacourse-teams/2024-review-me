import { useState } from 'react';

const STEP = {
  next: 1,
  prev: -1,
};

const useCurrentCardIndex = () => {
  const [currentCardIndex, setCurrentCardIndex] = useState(0);

  const handleCurrentCardIndex = (direction: 'prev' | 'next' | number) => {
    if (typeof direction === 'number') {
      setCurrentCardIndex(direction);
    } else {
      setCurrentCardIndex((prev) => prev + STEP[direction]);
    }
  };

  return {
    currentCardIndex,
    handleCurrentCardIndex,
  };
};
export default useCurrentCardIndex;
