import { useEffect, useLayoutEffect, useRef, useState } from 'react';

interface UseAccordionProps {
  isInitiallyOpened: boolean;
}

const useAccordion = ({ isInitiallyOpened }: UseAccordionProps) => {
  const [isOpened, setIsOpened] = useState(isInitiallyOpened);
  const [contentHeight, setContentHeight] = useState(0);
  const [isFirstRender, setIsFirstRender] = useState(true);
  const contentRef = useRef<HTMLDivElement>(null);

  useLayoutEffect(() => {
    if (!contentRef.current) return;

    setContentHeight(contentRef.current.clientHeight);
  }, []);

  // contentHeight가 계산된 이후에 isFirstRender 조작
  useEffect(() => {
    if (contentHeight > 0) {
      setIsFirstRender(false);
    }
  }, [contentHeight]);

  const handleAccordionButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  return {
    isOpened,
    contentHeight,
    contentRef,
    isFirstRender,
    handleAccordionButtonClick,
  };
};

export default useAccordion;
