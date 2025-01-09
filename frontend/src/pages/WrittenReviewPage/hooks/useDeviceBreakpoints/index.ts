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
const useDeviceBreakpoints = () => {
  const [breakpointType, setBreakPointType] = useState<Breakpoints | null>(null);
  const breakpointsArray = Object.entries(breakpoint);

  const getDeviceType = (breakpointType: Breakpoints | null): CurrentDevice => ({
    isMobile: breakpointType === 'xSmall' || breakpointType === 'xxSmall',
    isTablet: breakpointType === 'small' || breakpointType === 'medium',
    isDesktop: breakpointType === 'large',
  });

  const handleResize = () => {
    const currentWidth = window.innerWidth;
    const matchedBreakpoint = breakpointsArray.find(([, width]) => currentWidth <= width);

    setBreakPointType((matchedBreakpoint?.[0] as Breakpoints) ?? null);
  };

  useLayoutEffect(() => {
    handleResize();
    window.addEventListener('resize', handleResize);

    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return {
    breakpointType,
    deviceType: getDeviceType(breakpointType),
  };
};

export default useDeviceBreakpoints;
