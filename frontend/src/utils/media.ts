import theme from '@/styles/theme';
import { Breakpoints } from '@/types/media';

type Media = { [key in Breakpoints]: string };

const { breakpoint } = theme;

const breakpointsKeyList = Object.keys(breakpoint) as Breakpoints[];

const media = breakpointsKeyList.reduce((prev, key, index) => {
  const mediaType = index === breakpointsKeyList.length - 1 ? 'min' : 'max';

  prev[key] = `@media (${mediaType}-width: ${breakpoint[key]}px)`;
  return prev;
}, {} as Media);

export default media;
