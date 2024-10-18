import React, { useState } from 'react';

interface UseLongPressProps {
  handleLongPress: (event: React.MouseEvent | React.TouchEvent) => void;
  longPressDuration?: number;
}

const useLongPress = ({ handleLongPress, longPressDuration = 500 }: UseLongPressProps) => {
  const [pressTimer, setPressTimer] = useState<NodeJS.Timeout | null>(null);

  const startPressTimer = (event: React.MouseEvent | React.TouchEvent) => {
    const timer = setTimeout(() => {
      handleLongPress(event);
    }, longPressDuration);
    setPressTimer(timer);
  };

  const clearPressTimer = () => {
    // 사용자가 누르는 동작을 멈추면 타이머를 클리어
    if (pressTimer) {
      clearTimeout(pressTimer);
      setPressTimer(null);
    }
  };

  return {
    startPressTimer,
    clearPressTimer,
  };
};

export default useLongPress;
