import { useState, useLayoutEffect } from 'react';

import { breakpoint } from '@/styles/theme';
import { Breakpoints } from '@/types/media';

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

    // 마지막 breakpoint만 특정 범위 사이의 width 값이 아닌, 해당 기준 이상인 값이므로 따로 처리
    const inRangeBreakpoint = breakpointsArray.find(([, width]) => currentWidth <= width);
    const upperBoundBreakpoint = breakpointsArray[breakpointsArray.length - 1];

    const finalBreakpoint = inRangeBreakpoint || upperBoundBreakpoint;

    setBreakPointType((finalBreakpoint[0] as Breakpoints) ?? null);
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
