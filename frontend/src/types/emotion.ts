import '@emotion/react';

import { colors, fontSize, fontWeight, zIndex, breakpoints, sidebarWidth, borderRadius } from '../styles/theme';

// TODO: export 해서 사용하지 않다면 리팩토링
export type colorType = typeof colors;
export type zIndexType = typeof zIndex;
export type fontSizeType = typeof fontSize;
export type fontWeightType = typeof fontWeight;
export type breakpoints = typeof breakpoints;
export type sidebarWidth = typeof sidebarWidth;
export type borderRadius = typeof borderRadius;

type ThemeType = {
  fontSize: fontSizeType;
  fontWeight: fontWeightType;
  colors: colorType;
  zIndex: zIndexType;
  breakpoints: breakpoints;
  sidebarWidth: sidebarWidth;
  borderRadius: borderRadius;
  formWidth: string;
};

declare module '@emotion/react' {
  export interface Theme extends ThemeType {}
}
