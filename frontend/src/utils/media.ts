import { css, SerializedStyles } from '@emotion/react';
import { CSSObject } from '@emotion/styled';

import theme from '@/styles/theme';

export type Breakpoints = 'xxSmall' | 'xSmall' | 'small' | 'medium' | 'large';

const { breakpointsWidth } = theme;

export const breakpoints = Object.keys(breakpointsWidth).reduce(
  (acc, key) => {
    const size = breakpointsWidth[key as keyof typeof breakpointsWidth];
    const mediaType = key === 'large' ? 'min' : 'max';

    return {
      ...acc,
      [key]: `@media (${mediaType}-width: ${size})`,
    };
  },
  {} as Record<keyof typeof breakpointsWidth, string>,
);

const media = Object.entries(breakpoints).reduce(
  (acc, [key, value]) => {
    acc[key as Breakpoints] = (
      styles: CSSObject | TemplateStringsArray,
      ...interpolations: Array<CSSObject | SerializedStyles>
    ) => css`
      ${value} {
        ${css(styles, ...interpolations)}
      }
    `;
    return acc;
  },
  {} as Record<
    Breakpoints,
    (
      styles: CSSObject | TemplateStringsArray,
      ...interpolations: Array<CSSObject | SerializedStyles>
    ) => SerializedStyles
  >,
);

export default media;
