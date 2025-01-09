import { useState, useLayoutEffect } from 'react';

import { breakpoint } from '@/styles/theme';
import { Breakpoints } from '@/utils/media';

interface CurrentDevice {
  isMobile: boolean;
  isTablet: boolean;
  isDesktop: boolean;
}

/**
  현재 미디어 쿼리 상태와 디바이스 종류(boolean)를 리턴하는 훅 
 */
const useCurrentMediaType = () => {
  const [currentMediaType, setCurrentMediaType] = useState<Breakpoints | null>(null);
  const breakpointsArray = Object.entries(breakpoint);

  const getCurrentDeviceType = (mediaType: Breakpoints | null): CurrentDevice => ({
    isMobile: mediaType === 'xSmall' || mediaType === 'xxSmall',
    isTablet: mediaType === 'small' || mediaType === 'medium',
    isDesktop: mediaType === 'large',
  });

  useLayoutEffect(() => {
    const handleResize = () => {
      const currentWidth = window.innerWidth;
      const matchedBreakpoint = breakpointsArray.find(([, width]) => currentWidth <= width);

      setCurrentMediaType((matchedBreakpoint?.[0] as Breakpoints) ?? null);
    };

    handleResize();

    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return {
    currentMediaType,
    currentDeviceType: getCurrentDeviceType(currentMediaType),
  };
};

export default useCurrentMediaType;
