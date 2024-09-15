import '@emotion/react';

import {
  colors,
  fontSize,
  fontWeight,
  zIndex,
  breakpoints,
  scrollbarWidth,
  borderRadius,
  componentHeight,
  confirmModalSize,
} from '../styles/theme';

// TODO: export 해서 사용하지 않다면 리팩토링
export type Color = typeof colors;
export type ZIndex = typeof zIndex;
export type FontSize = typeof fontSize;
export type FontWeight = typeof fontWeight;
export type Breakpoints = typeof breakpoints;
export type ScrollbarWidthStyle = typeof scrollbarWidth;
export type BorderRadius = typeof borderRadius;
export type ComponentHeight = typeof componentHeight;
export type ConfirmModalSize = typeof confirmModalSize;

type ThemeType = {
  fontSize: FontSize;
  fontWeight: FontWeight;
  colors: Color;
  zIndex: ZIndex;
  breakpoints: Breakpoints;
  scrollbarWidth: ScrollbarWidthStyle;
  borderRadius: BorderRadius;
  formWidth: string;
  componentHeight: ComponentHeight;
  confirmModalSize: ConfirmModalSize;
};

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
