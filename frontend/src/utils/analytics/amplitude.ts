import * as amplitude from '@amplitude/analytics-browser';

export const startAmplitude = () => {
  if (!process.env.APMLITUDE_KEY) return;

  amplitude.init(process.env.APMLITUDE_KEY, { autocapture: false });
};

const getBrowserInfo = () => {
  let browserName = 'Unknown';
  let browserVersion = 'Unknown';
  const userAgent = navigator.userAgent;

  const BROWSER_LIST = [
    { name: 'Firefox', versionToken: 'Firefox' },
    { name: 'Chrome', versionToken: 'Chrome' },
    { name: 'Safari', versionToken: 'Version', exclude: 'Chrome' },
    { name: 'Edge', versionToken: 'Edg' },
    { name: 'Internet Explorer', versionToken: 'MSIE' },
  ];

  for (const browser of BROWSER_LIST) {
    if (
      userAgent.indexOf(browser.versionToken) > -1 &&
      (!browser.exclude || userAgent.indexOf(browser.exclude) === -1)
    ) {
      browserName = browser.name;
      browserVersion = userAgent
        .substring(userAgent.indexOf(browser.versionToken) + browser.versionToken.length + 1)
        .split(' ')[0];
      break;
    }
  }

  return { browserName, browserVersion };
};

/**
 * 사용자가 사용한 이벤트를 추적하는 메서드
 * @param eventName 이벤트 이름
 * @param eventProps 사용자 행동 데이터에 추가적으로 들어갈 내용들
 */
export const trackEventInAmplitude = (eventName: string, eventProps: Record<string, any> = {}) => {
  if (!process.env.APMLITUDE_KEY) return;

  const PATHNAME = {
    release: 'review-me.page',
    dev: 'dev.review-me.page',
  };
  const DOMAIN_MAPPING = {
    [PATHNAME.release]: 'release',
    [PATHNAME.dev]: 'dev',
  };

  const { hostname } = window.location;
  const domainName = DOMAIN_MAPPING[hostname] || 'local';

  const { browserName, browserVersion } = getBrowserInfo();

  amplitude.track(eventName, {
    domain: domainName,
    browserName,
    browserVersion,
    ...eventProps,
  });
};
