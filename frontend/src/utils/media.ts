import theme from '@/styles/theme';

const { breakpoint } = theme;

export type Breakpoints = keyof typeof breakpoint;
type Media = { [key in Breakpoints]: string };

const breakpointsKeyList = Object.keys(breakpoint) as Breakpoints[];

const media = breakpointsKeyList.reduce((prev, key, index) => {
  const mediaType = index === breakpointsKeyList.length - 1 ? 'min' : 'max';

  prev[key] = `@media (${mediaType}-width: ${breakpoint[key]}px)`;
  return prev;
}, {} as Media);

export default media;
