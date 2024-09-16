import { css, SerializedStyles, Theme } from '@emotion/react';
import { CSSObject } from '@emotion/styled';

export type Breakpoints = 'xxSmall' | 'xSmall' | 'small' | 'medium' | 'large';

export const breakpoints: Record<Breakpoints, string> = {
  xxSmall: '@media (max-width: 320px)',
  xSmall: '@media (max-width: 425px)',
  small: '@media (max-width: 768px)',
  medium: '@media (max-width: 1024px)',
  large: '@media (min-width: 1025px)',
};

type CSSParams = CSSObject | TemplateStringsArray;
type MediaStyles = CSSParams | ((props: { theme: Theme }) => CSSParams);

const media = Object.entries(breakpoints).reduce(
  (acc, [key, value]) => {
    acc[key as Breakpoints] = (styles: MediaStyles, ...interpolations: Array<CSSParams>) => {
      return (props: { theme: Theme }) => css`
        ${value} {
          ${typeof styles === 'function' ? styles(props) : css(styles, ...interpolations)}
        }
      `;
    };
    return acc;
  },
  {} as Record<
    Breakpoints,
    (styles: MediaStyles, ...interpolations: Array<CSSParams>) => (props: { theme: Theme }) => SerializedStyles
  >,
);

export default media;
