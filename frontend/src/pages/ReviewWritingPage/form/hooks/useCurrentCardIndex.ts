import { useLayoutEffect, useState } from 'react';

import { Direction } from '../../types';

const STEP = {
  next: 1,
  prev: -1,
};

const useCurrentCardIndex = () => {
  const [currentCardIndex, setCurrentCardIndex] = useState(0);

  const handleCurrentCardIndex = (direction: Direction) => {
    if (typeof direction === 'number') {
      setCurrentCardIndex(direction);
    } else {
      setCurrentCardIndex((prev) => prev + STEP[direction]);
    }
  };

  useLayoutEffect(() => {
    window.scrollTo({ top: 0 });
  }, [currentCardIndex]);

  return {
    currentCardIndex,
    handleCurrentCardIndex,
  };
};
export default useCurrentCardIndex;
