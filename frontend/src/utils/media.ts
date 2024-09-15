import { css, SerializedStyles } from '@emotion/react';
import { CSSObject } from '@emotion/styled';

export type Breakpoints = 'xxSmall' | 'xSmall' | 'small' | 'medium' | 'large';

export const breakpoints: Record<Breakpoints, string> = {
  xxSmall: '@media (max-width: 320px)',
  xSmall: '@media (max-width: 425px)',
  small: '@media (max-width: 768px)',
  medium: '@media (max-width: 1024px)',
  large: '@media (min-width: 1025px)',
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
