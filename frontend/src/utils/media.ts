import theme from '@/styles/theme';

const { breakpoints } = theme;

type Media = {
  [key in keyof typeof breakpoints]: string;
};

export const media = Object.keys(breakpoints).reduce((acc, key) => {
  const size = breakpoints[key as keyof typeof breakpoints];
  const mediaType = key === 'large' ? 'min' : 'max';

  return {
    ...acc,
    [key]: `@media (${mediaType}-width: ${size})`,
  };
}, {} as Media);

export default media;
