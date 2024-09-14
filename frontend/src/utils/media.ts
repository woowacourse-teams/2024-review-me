import { css, SerializedStyles } from '@emotion/react';
import { CSSObject } from '@emotion/styled';

export type Breakpoints = 'mobile' | 'tablet' | 'desktop';

export const breakpoints: Record<Breakpoints, string> = {
  mobile: '@media (max-width: 768px)',
  tablet: '@media (max-width: 1024px)',
  desktop: '@media (min-width: 1025px)',
};

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
