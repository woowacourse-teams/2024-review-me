import '@emotion/react';

import {
  colors,
  fontSize,
  fontWeight,
  zIndex,
  breakpoints,
  sidebarWidth,
  borderRadius,
} from '../styles/theme';

// TODO: export 해서 사용하지 않다면 리팩토링
export type Color = typeof colors;
export type ZIndex = typeof zIndex;
export type FontSize = typeof fontSize;
export type FontWeight = typeof fontWeight;
export type Breakpoints = typeof breakpoints;
export type SidebarWidthStyle = typeof sidebarWidth;
export type BorderRadius = typeof borderRadius;

type ThemeType = {
  fontSize: FontSize;
  fontWeight: FontWeight;
  colors: Color;
  zIndex: ZIndex;
  breakpoints: Breakpoints;
  sidebarWidth: SidebarWidthStyle;
  borderRadius: BorderRadius;
  formWidth: string;
  footerHeight: string;
};

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
