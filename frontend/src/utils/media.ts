import theme from '@/styles/theme';

const { breakpoints } = theme;

type BreakpointsKey = keyof typeof breakpoints;

type Media = {
  [key in BreakpointsKey]: string;
};

export const media = Object.keys(breakpoints).reduce((acc, key) => {
  const size = breakpoints[key as BreakpointsKey];
  const mediaType = key === 'large' ? 'min' : 'max';

  return {
    ...acc,
    [key]: `@media (${mediaType}-width: ${size})`,
  };
}, {} as Media);

export default media;
