import { useCallback, useEffect } from 'react';
import { useLocation } from 'react-router';

import { PAGE_VISITED_EVENT_NAME } from '@/constants';
import { ROUTE } from '@/constants/route';
import { PageName } from '@/types';
import { trackEventInAmplitude } from '@/utils';

const useTrackVisitedPageInAmplitude = () => {
  const location = useLocation();

  const getPageName = useCallback((): PageName => {
    if (location.pathname === ROUTE.home) return 'home';

    const pageName = Object.entries(ROUTE).find(
      ([key, value]) => key !== 'home' && location.pathname.includes(value),
    )?.[0] as PageName;

    return pageName;
  }, [location.pathname]);

  const trackVisitedPageInAmplitude = (pageName: PageName) => {
    if (!pageName) return console.error('페이지 이름을 찾을 수 없어요.');

    const eventName = PAGE_VISITED_EVENT_NAME[pageName];
    trackEventInAmplitude(eventName);
  };

  useEffect(() => {
    const pageName = getPageName();
    trackVisitedPageInAmplitude(pageName);
  }, [getPageName]);
};

export default useTrackVisitedPageInAmplitude;
