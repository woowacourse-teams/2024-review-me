import { RefObject } from 'react';

const scrollToTop = (container?: RefObject<HTMLElement> | null) => {
  const scrollOption: ScrollToOptions = {
    top: 0,
    behavior: 'smooth',
  };

  if (container?.current) {
    container.current.scrollTo(scrollOption);
    return;
  }

  window.scrollTo(scrollOption);
};

export default scrollToTop;
